(async function () {
  'use strict';

  importScripts('dexie.min.js');
  let apiUrl, environment, appConfig, apiEndPoint;
  const db = new Dexie("CPDSS");
  db.version(1).stores({
    cargoNominations: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
    ports: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
    ohq: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
    obq: "++,storeKey,timeStamp,vesselId,voyageId,loadableStudyId,status",
    dischargePorts: "++,storeKey,timeStamp,vesselId,voyageId,dischargeStudyId,status",
    dischargeOhq: "++,storeKey,timeStamp,vesselId,voyageId,dischargeStudyId,status",
    loadingInformations: "++,storeKey,timeStamp,vesselId,voyageId,loadingInfoId,status",
    properties: ""
  });

  const isOpen = await db?.open();
  if (isOpen) {
    // Code for calling sync function in interval .Please dont remove this code
    setInterval(async () => {
      if (!environment) {
        environment = await db.properties.get('environment');
      }

      if (!appConfig) {
        appConfig = await db.properties.get('appConfig');
      }

      if (environment !== 'shore') {
        apiEndPoint = '/api/ship';
      } else {
        apiEndPoint = '/api/cloud';
      }

      if (appConfig?.path) {
        apiEndPoint = appConfig?.path + apiEndPoint;
      }

      if (self.location.protocol === 'https:') {
        apiUrl = `${self.location.protocol}//${self.location.hostname}:${self.location.port}${apiEndPoint}`;
      } else {
        const port = 8085;
        const hostName = environment === 'shore' ? '13.251.141.12' : '13.251.226.207';
        apiUrl = `${self.location.protocol}//${hostName}:${port}${apiEndPoint}`;
      }

      const token = await getToken();

      if (token) {
        serverSyncCargoNomination(token);
        serverSyncPorts(token);
        serverSyncOHQ(token);
        serverSyncOBQ(token);
        serverSyncDischargeChargeOHQ(token);
        serverSyncDischargeChargePorts(token);
      }
    }, 2000);
  }

  // Function to get token from index DB
  async function getToken() {
    try {
      return await db?.properties?.get('token');
    } catch (error) {
      const isOpen = await db?.open();
      if (isOpen) {
        return await getToken();
      }
    }
  }


  /**
   * Fuction for sync of indexdb and server for cargo nomination grid
   *
   */
  async function serverSyncCargoNomination(token) {
    // Remove all records with api initiated as status true
    db.cargoNominations.where({ 'status': 1 }).delete();

    //Get all store keys
    await db.cargoNominations.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
        const primaryKey = await db.cargoNominations.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        if (primaryKey?.length) {
          //Get last update record of particular store key
          const cargoNomination = await db.cargoNominations.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
          if (cargoNomination) {
            const updated = await db.cargoNominations.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
            if (updated) {
              if (cargoNomination?.isDelete) {
                // send delete sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${cargoNomination?.vesselId}/voyages/${cargoNomination?.voyageId}/loadable-studies/${cargoNomination?.loadableStudyId}/cargo-nominations/${cargoNomination.id}`, {
                  method: 'DELETE',
                  headers: headers
                });

                if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                  const sync = await syncResponse.json();
                  sync.storeKey = cargoNomination.storeKey;
                  sync.type = 'cargo_nomination_sync_finished';
                  const refreshedToken = syncResponse.headers.get('token');
                  sync.refreshedToken = refreshedToken;
                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.cargoNominations.delete(primaryKey))
                  return notifyClients(sync);
                }

                return Promise.reject('sync failed: ' + syncResponse.status);
              } else {
                // send update or add sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token

                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${cargoNomination?.vesselId}/voyages/${cargoNomination?.voyageId}/loadable-studies/${cargoNomination?.loadableStudyId}/cargo-nominations/${cargoNomination?.id}`, {
                  method: 'POST',
                  body: JSON.stringify(cargoNomination),
                  headers: headers
                });

                if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                  const sync = await syncResponse.json();
                  sync.storeKey = cargoNomination.storeKey;
                  sync.type = 'cargo_nomination_sync_finished';
                  const refreshedToken = syncResponse.headers.get('token');
                  sync.refreshedToken = refreshedToken;
                  // update id of cargo nomination if there are any new rows with same storekey
                  const updated = await db.cargoNominations.where({ 'storeKey': key }).modify({ 'id': cargoNomination?.id });
                  if (updated) {
                    //on success of api call remove all rows of selected primary keys
                    primaryKey.forEach(async (primaryKey) => await db.cargoNominations.delete(primaryKey));
                  }
                  return notifyClients(sync);
                }

                return Promise.reject('sync failed: ' + syncResponse.status);
              }
            }
          }
        }
      });
    });
  }

  /**
   * Fuction for sync of port indexdb and server
   *
   */
  async function serverSyncPorts(token) {
    // Remove all records with api initiated as status true
    db.ports.where({ 'status': 1 }).delete();

    await db.ports.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
        const primaryKey = await db.ports.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        //Get last update record of particular store key
        const port = await db.ports.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
        if (port) {
          const updated = await db.ports.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
          if (updated) {
            if (port?.isDelete) {
              // send delete sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/loadable-studies/${port?.loadableStudyId}/ports/${port.id}`, {
                method: 'DELETE',
                headers: headers
              });

              if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                const sync = await syncResponse.json();
                sync.storeKey = port.storeKey;
                sync.type = 'ports_sync_finished';
                const refreshedToken = syncResponse.headers.get('token');
                sync.refreshedToken = refreshedToken;
                //on success of api call remove all rows of selected primary keys
                primaryKey.forEach(async (primaryKey) => await db.ports.delete(primaryKey))
                return notifyClients(sync);
              }

              return Promise.reject('sync failed: ' + syncResponse.status);
            } else {
              // send update or add sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/loadable-studies/${port?.loadableStudyId}/ports/${port?.id}`, {
                method: 'POST',
                body: JSON.stringify(port),
                headers: headers
              });

              if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                const sync = await syncResponse.json();
                sync.storeKey = port.storeKey;
                sync.type = 'ports_sync_finished';
                const refreshedToken = syncResponse.headers.get('token');
                sync.refreshedToken = refreshedToken;
                // update id of port if there are any new rows with same storekey
                const updated = await db.ports.where({ 'storeKey': key }).modify({ 'id': port?.id });
                if (updated) {
                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.ports.delete(primaryKey));
                }

                return notifyClients(sync);
              }

              return Promise.reject('sync failed: ' + syncResponse.status);
            }
          }
        }
      });
    });
  }


    /**
   * Fuction for sync of discharge port indexdb and server
   *
   */
     async function serverSyncDischargeChargePorts(token) {
      // Remove all records with api initiated as status true
      db.dischargePorts.where({ 'status': 1 }).delete();
  
      await db.dischargePorts.orderBy('storeKey').uniqueKeys((storeKeys) => {
        storeKeys.forEach(async (key) => {
          const timeStamp = Date.now - 60000;
          //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
          const primaryKey = await db.dischargePorts.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();
  
          //Get last update record of particular store key
          const port = await db.dischargePorts.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
          if (port) {
            const updated = await db.dischargePorts.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
            if (updated) {
              if (port?.isDelete) {
                // send delete sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/discharge-studies/${port?.dischargeStudyId}/ports/${port.id}`, {
                  method: 'DELETE',
                  headers: headers
                });
  
                if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                  const sync = await syncResponse.json();
                  sync.storeKey = port.storeKey;
                  sync.type = 'discharge_ports_sync_finished';
                  const refreshedToken = syncResponse.headers.get('token');
                  sync.refreshedToken = refreshedToken;
                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.dischargePorts.delete(primaryKey))
                  return notifyClients(sync);
                }
  
                return Promise.reject('sync failed: ' + syncResponse.status);
              } else {
                // send update or add sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/discharge-studies/${port?.dischargeStudyId}/ports/${port?.id}`, {
                  method: 'POST',
                  body: JSON.stringify(port),
                  headers: headers
                });
  
                if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                  const sync = await syncResponse.json();
                  sync.storeKey = port.storeKey;
                  sync.type = 'discharge_ports_sync_finished';
                  const refreshedToken = syncResponse.headers.get('token');
                  sync.refreshedToken = refreshedToken;
                  // update id of port if there are any new rows with same storekey
                  const updated = await db.dischargePorts.where({ 'storeKey': key }).modify({ 'id': port?.id });
                  if (updated) {
                    //on success of api call remove all rows of selected primary keys
                    primaryKey.forEach(async (primaryKey) => await db.dischargePorts.delete(primaryKey));
                  }
  
                  return notifyClients(sync);
                }
  
                return Promise.reject('sync failed: ' + syncResponse.status);
              }
            }
          }
        });
      });
    }
    
  /**
   * Fuction for sync of indexdb and server for ohq
   *
   */
  async function serverSyncOHQ(token) {
    // Remove all records with api initiated as status true
    db.ohq.where({ 'status': 1 }).delete();

    //Get all store keys
    await db.ohq.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
        const primaryKey = await db.ohq.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        if (primaryKey?.length) {
          //Get last update record of particular store key
          const ohq = await db.ohq.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
          if (ohq) {
            const updated = await db.ohq.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
            if (updated) {
              // send update or add sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${ohq?.vesselId}/voyages/${ohq?.voyageId}/loadable-studies/${ohq?.loadableStudyId}/port-rotation/${ohq?.portRotationId}/on-hand-quantities/${ohq?.id}`, {
                method: 'POST',
                body: JSON.stringify(ohq),
                headers: headers
              });

              if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                const sync = await syncResponse.json();
                sync.storeKey = ohq.storeKey;
                sync.type = 'ohq_sync_finished';
                const refreshedToken = syncResponse.headers.get('token');
                sync.refreshedToken = refreshedToken;
                // update id of ohq if there are any new rows with same storekey
                const updated = await db.ohq.where({ 'storeKey': key }).modify({ 'id': ohq?.id });
                if (updated) {
                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.ohq.delete(primaryKey));
                }
                return notifyClients(sync);
              }

              return Promise.reject('sync failed: ' + syncResponse.status);
            }
          }
        }
      });
    });
  }

    /**
   * Fuction for sync of indexdb and server for ohq
   *
   */
     async function serverSyncDischargeChargeOHQ(token) {
      // Remove all records with api initiated as status true
      db.dischargeOhq.where({ 'status': 1 }).delete();
  
      //Get all store keys
      await db.dischargeOhq.orderBy('storeKey').uniqueKeys((storeKeys) => {
        storeKeys.forEach(async (key) => {
          const timeStamp = Date.now - 60000;
          //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
          const primaryKey = await db.dischargeOhq.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();
  
          if (primaryKey?.length) {
            //Get last update record of particular store key
            const ohq = await db.dischargeOhq.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
            if (ohq) {
              const updated = await db.dischargeOhq.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
              if (updated) {
                // send update or add sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${ohq?.vesselId}/voyages/${ohq?.voyageId}/discharge-studies/${ohq?.dischargeStudyId}/port-rotation/${ohq?.portRotationId}/on-hand-quantities/${ohq?.id}`, {
                  method: 'POST',
                  body: JSON.stringify(ohq),
                  headers: headers
                });
  
                if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                  const sync = await syncResponse.json();
                  sync.storeKey = ohq.storeKey;
                  sync.type = 'ohq_sync_finished';
                  const refreshedToken = syncResponse.headers.get('token');
                  sync.refreshedToken = refreshedToken;
                  // update id of ohq if there are any new rows with same storekey
                  const updated = await db.dischargeOhq.where({ 'storeKey': key }).modify({ 'id': ohq?.id });
                  if (updated) {
                    //on success of api call remove all rows of selected primary keys
                    primaryKey.forEach(async (primaryKey) => await db.dischargeOhq.delete(primaryKey));
                  }
                  return notifyClients(sync);
                }
  
                return Promise.reject('sync failed: ' + syncResponse.status);
              }
            }
          }
        });
      });
    }
  

  /**
   * Fuction for sync of indexdb and server for obq
   *
   */
  async function serverSyncOBQ(token) {
    // Remove all records with api initiated as status true
    db.obq.where({ 'status': 1 }).delete();

    //Get all store keys
    await db.obq.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey where status not equal to 1 (ie: all new records) or status equal to one and has been in pending state for more that 1 minute.
        const primaryKey = await db.obq.where({ 'storeKey': key }).and(data => data.status !== 1 || !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        if (primaryKey?.length) {
          //Get last update record of particular store key
          const obq = await db.obq.where({ ':id': primaryKey.sort((a, b) => b - a)[0] }).first();
          if (obq) {
            const updated = await db.obq.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now(), status: 1 });
            if (updated) {
              // send update or add sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${obq?.vesselId}/voyages/${obq?.voyageId}/loadable-studies/${obq?.loadableStudyId}/ports/${obq?.portId}/on-board-quantities/${obq?.id}`, {
                method: 'POST',
                body: JSON.stringify(obq),
                headers: headers
              });

              if (syncResponse.status === 200 || syncResponse.status === 400 || syncResponse.status === 401) {
                const sync = await syncResponse.json();
                sync.storeKey = obq.storeKey;
                sync.type = 'obq_sync_finished';
                const refreshedToken = syncResponse.headers.get('token');
                sync.refreshedToken = refreshedToken;
                // update id of obq if there are any new rows with same storekey
                const updated = await db.obq.where({ 'storeKey': key }).modify({ 'id': obq?.id });
                if (updated) {
                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.obq.delete(primaryKey));
                }
                return notifyClients(sync);
              }

              return Promise.reject('sync failed: ' + syncResponse.status);
            }
          }
        }
      });
    });
  }


  /**
   * Notify all clents of service worker with events
   *
   * @param {*} sync
   */
  async function notifyClients(sync) {
    const clients = await self.clients.matchAll({ includeUncontrolled: true });
    for (const client of clients) {
      client.postMessage(sync);
    }
  }

  const syncStore = {}
  self.addEventListener('message', event => {
    if (event.data.type === 'loadable-pattern-status') {
      // get a unique id to save the data
      const id = event.data.data.loadableStudyId;
      syncStore[id] = event.data
      // register a sync and pass the id as tag for it to get the data
      self.registration.sync.register(id)
    } else if (event.data.type === 'validate-and-save') {
      const id = event.data.data.loadablePatternId;
      syncStore[id] = event.data;
      self.registration.sync.register(id);
    } else if(event.data.type === 'discharge-study-pattern-status') {
      const id = event.data.data.dischargeStudyId;
      syncStore[id] = event.data;
      self.registration.sync.register(id);
    }
  })


  self.addEventListener('sync', function (event) {
    if (syncStore[event.tag].type === 'loadable-pattern-status') {
      event.waitUntil(checkLoadableStudyStatus(syncStore[event.tag].data));
    } else if (syncStore[event.tag].type === 'validate-and-save') {
      event.waitUntil(checkSaveAndValidateStatus(syncStore[event.tag].data));
    } else if (syncStore[event.tag].type === 'discharge-study-pattern-status') {
      event.waitUntil(checkDischargeStudyStatus(syncStore[event.tag].data));
    }
  });

  async function checkSaveAndValidateStatus(data) {
    let currentStatus;
    const timer = setInterval(async () => {
      var headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + await getToken()
      }
      const syncResponse = await fetch(`${apiUrl}/vessels/${data?.vesselId}/voyages/${data?.voyageId}/loadable-studies/${data?.loadableStudyId}/loadable-pattern-status`, {
        method: 'POST',
        body: JSON.stringify({ processId: data?.processId, loadablePatternId: data.loadablePatternId }),
        headers: headers
      });
      const syncView = await syncResponse.json();
      const refreshedToken = syncResponse.headers.get('token');
      const sync = {};
      sync.refreshedToken = refreshedToken;
      sync.pattern = data;
      if (syncView?.responseStatus?.status === '200') {
        sync.status = syncView?.responseStatus?.status;
        if (syncView?.loadableStudyStatusId === 12) {
          clearInterval(timer);
          sync.type = 'loadable-pattern-validation-success';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        } else if (syncView?.loadableStudyStatusId === 13) {
          clearInterval(timer);
          sync.type = 'loadable-pattern-validation-failed';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        } else if (syncView?.loadableStudyStatusId === 14) {
          sync.type = 'loadable-pattern-validation-started';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
      }
      else if (syncView?.status === '401' || syncView?.status === '400') {
        notifyClients(syncView);
      }
      else if (syncView?.responseStatus?.status === '500') {
        clearInterval(timer);
      }
    }, 5000);
  }

  /**
   * Method for monitoring loadable study status after pattern generation is started
   *
   * @param {*} data
   */
  async function checkLoadableStudyStatus(data) {
    let currentStatus;
    const sync = {};
    const timer = setInterval(async () => {
      var headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + await getToken()
      }
      const syncResponse = await fetch(`${apiUrl}/vessels/${data?.vesselId}/voyages/${data?.voyageId}/loadable-studies/${data?.loadableStudyId}/loadable-pattern-status`, {
        method: 'POST',
        body: JSON.stringify({ processId: data?.processId }),
        headers: headers
      });
      const syncView = await syncResponse.json();
      const refreshedToken = syncResponse.headers.get('token');
      sync.refreshedToken = refreshedToken;
      sync.pattern = data;
      sync.syncType = 'loadable-study-pattern-status';
      if (syncView?.responseStatus?.status === '200') {
        sync.status = syncView?.responseStatus?.status;
        currentStatus = syncView?.loadableStudyStatusId;
        if (syncView?.loadableStudyStatusId === 21) {
          sync.type = 'loadable-study-communicated-to-shore';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
        if (syncView?.loadableStudyStatusId === 4 || syncView?.loadableStudyStatusId === 5) {
          sync.type = 'loadable-pattern-processing';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
        if (syncView?.loadableStudyStatusId === 7) {
          sync.type = 'loadable-pattern-loadicator-checking';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
        if (syncView?.loadableStudyStatusId === 3) {
          clearInterval(timer);
          sync.type = 'loadable-pattern-completed';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
        if (syncView?.loadableStudyStatusId === 6) {
          clearInterval(timer);
          sync.type = 'loadable-pattern-no-solution';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
        if (syncView?.loadableStudyStatusId === 11) {
          clearInterval(timer);
          sync.type = 'loadable-pattern-error-occured';
          sync.statusId = syncView?.loadableStudyStatusId;
          notifyClients(sync);
        }
      } else if (syncView?.status === '401' || syncView?.status === '400') {
        notifyClients(syncView);
      }
      else if (syncView?.responseStatus?.status === '500') {
        clearInterval(timer);
      }
    }, 3500);
    setTimeout(() => {
      if (currentStatus === 4) {
        sync.type = 'loadable-pattern-no-response';
        // sending default status
        sync.statusId = 1;
        notifyClients(sync);
        clearInterval(timer);
      }
    }, 7200000);
  }

    /**
   * Method for monitoring discharge study status after plan generetion is started
   *
   * @param {*} data
   */
     async function checkDischargeStudyStatus(data) {
      let currentStatus;
      const sync = {};
      const timer = setInterval(async () => {
        var headers = {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + await getToken()
        }
        const syncResponse = await fetch(`${apiUrl}/vessels/${data?.vesselId}/voyages/${data?.voyageId}/discharge-studies/${data?.dischargeStudyId}/discharge-pattern-status`, {
          method: 'POST',
          body: JSON.stringify({ processId: data?.processId }),
          headers: headers
        });
        const syncView = await syncResponse.json();
        const refreshedToken = syncResponse.headers.get('token');
        sync.refreshedToken = refreshedToken;
        sync.pattern = data;
        sync.syncType = 'discharge-study-plan-status';
        if (syncView?.responseStatus?.status === '200') {
          sync.status = syncView?.responseStatus?.status;
          currentStatus = syncView?.dischargeStudyId;
          if (syncView?.loadableStudyStatusId === 21) {
            sync.type = 'loadable-study-communicated-to-shore';
            sync.statusId = syncView?.loadableStudyStatusId;
            notifyClients(sync);
          }
          if (syncView?.dischargeStudyId === 4 || syncView?.dischargeStudyId === 5) {
            sync.type = 'discharge-pattern-processing';
            sync.statusId = syncView?.dischargeStudyId;
            notifyClients(sync);
          }
          if (syncView?.dischargeStudyId === 7) {
            sync.type = 'discharge-pattern-loadicator-checking';
            sync.statusId = syncView?.dischargeStudyId;
            notifyClients(sync);
          }
          if (syncView?.dischargeStudyId === 3) {
            clearInterval(timer);
            sync.type = 'discharge-pattern-completed';
            sync.statusId = syncView?.dischargeStudyId;
            notifyClients(sync);
          }
          if (syncView?.dischargeStudyId === 6) {
            clearInterval(timer);
            sync.type = 'discharge-pattern-no-solution';
            sync.statusId = syncView?.dischargeStudyId;
            notifyClients(sync);
          }
          if (syncView?.loadableStudyStatusId === 11) {
            clearInterval(timer);
            sync.type = 'discharge-pattern-error-occured';
            sync.statusId = syncView?.dischargeStudyId;
            notifyClients(sync);
          }
        } else if (syncView?.status === '401' || syncView?.status === '400') {
          notifyClients(syncView);
        }
        else if (syncView?.responseStatus?.status === '500') {
          clearInterval(timer);
        }
      }, 3500);
      setTimeout(() => {
        if (currentStatus === 4) {
          sync.type = 'discharge-pattern-no-response';
          // sending default status
          sync.statusId = 1;
          notifyClients(sync);
          clearInterval(timer);
        }
      }, 7200000);
    }


}());
