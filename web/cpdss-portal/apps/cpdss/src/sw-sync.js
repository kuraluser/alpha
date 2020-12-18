(function () {
  'use strict';

  importScripts('dexie.min.js');

  const db = new Dexie("CPDSS");
  let apiUrl;
  if (self.location.protocol === 'https:') {
    apiUrl = `${self.location.protocol}//${self.location.hostname}:${self.location.port}/api/cloud`;
  } else {
    apiUrl = `${self.location.protocol}//192.168.2.89:8085/api/cloud`;
  }



  db.version(1).stores({
    cargoNominations: "++,storeKey,timeStamp",
    ports: "++,storeKey,timeStamp",
    ohq: "++,storeKey",
    obq: "++,storeKey,timeStamp",
  });
  db.open();

  // Code for calling sync function in interval .Please dont remove this code
  setInterval(() => {
    serverSyncCargoNomination();
    serverSyncPorts();
    serverSyncOHQ();
    serverSyncOBQ();
  }, 5000);

  /**
   * Fuction for sync of indexdb and server for cargo nomination grid
   *
   */
  async function serverSyncCargoNomination() {
    //Get all store keys
    await db.cargoNominations.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey
        const primaryKey = await db.cargoNominations.where({ 'storeKey': key }).and(data => !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        if (primaryKey?.length) {
          //Get last update record of particular store key
          const cargoNomination = await db.cargoNominations.where({ ':id': primaryKey.sort((a, b) => a > b ? a : b)[0] }).first();
          if (cargoNomination) {
            const updated = await db.cargoNominations.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now() });
            if (updated) {
              if (cargoNomination?.isDelete) {
                // send delete sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${cargoNomination?.vesselId}/voyages/${cargoNomination?.voyageId}/loadable-studies/${cargoNomination?.loadableStudyId}/cargo-nominations/${cargoNomination.id}`, {
                  method: 'DELETE',
                  headers: headers
                });

                if (syncResponse.status === 200) {
                  const sync = await syncResponse.json();
                  sync.storeKey = cargoNomination.storeKey;
                  sync.type = 'cargo_nomination_sync_finished';

                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.cargoNominations.delete(primaryKey))
                  return notifyClients(sync);
                }

                return Promise.reject('sync failed: ' + syncResponse.status);
              } else {
                // send update or add sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${cargoNomination?.vesselId}/voyages/${cargoNomination?.voyageId}/loadable-studies/${cargoNomination?.loadableStudyId}/cargo-nominations/${cargoNomination?.id}`, {
                  method: 'POST',
                  body: JSON.stringify(cargoNomination),
                  headers: headers
                });

                if (syncResponse.status === 200) {
                  const sync = await syncResponse.json();
                  sync.storeKey = cargoNomination.storeKey;
                  sync.type = 'cargo_nomination_sync_finished';

                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.cargoNominations.delete(primaryKey))
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
  async function serverSyncPorts() {
    await db.ports.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey
        const primaryKey = await db.ports.where({ 'storeKey': key }).and(data => !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        //Get last update record of particular store key
        const port = await db.ports.where({ ':id': primaryKey.sort((a, b) => a > b ? a : b)[0] }).first();
        if (port) {
          const updated = await db.ports.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now() });
          if (updated) {
            if (port?.isDelete) {
              // send delete sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/loadable-studies/${port?.loadableStudyId}/ports/${port.id}`, {
                method: 'DELETE',
                headers: headers
              });

              if (syncResponse.status === 200) {
                const sync = await syncResponse.json();
                sync.storeKey = port.storeKey;
                sync.type = 'ports_sync_finished';

                //on success of api call remove all rows of selected primary keys
                primaryKey.forEach(async (primaryKey) => await db.ports.delete(primaryKey))
                return notifyClients(sync);
              }

              return Promise.reject('sync failed: ' + syncResponse.status);
            } else {
              // send update or add sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${port?.vesselId}/voyages/${port?.voyageId}/loadable-studies/${port?.loadableStudyId}/ports/${port?.id}`, {
                method: 'POST',
                body: JSON.stringify(port),
                headers: headers
              });

              if (syncResponse.status === 200) {
                const sync = await syncResponse.json();
                sync.storeKey = port.storeKey;
                sync.type = 'ports_sync_finished';

                //on success of api call remove all rows of selected primary keys
                primaryKey.forEach(async (primaryKey) => await db.ports.delete(primaryKey))
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
  async function serverSyncOHQ() {
    //Get all store keys
    await db.ohq.orderBy('storeKey').uniqueKeys((storeKeys) => {
      storeKeys.forEach(async (key) => {
        const timeStamp = Date.now - 60000;
        //Get all primary keys with storekey
        const primaryKey = await db.ohq.where({ 'storeKey': key }).and(data => !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

        if (primaryKey?.length) {
          //Get last update record of particular store key
          const ohq = await db.ohq.where({ ':id': primaryKey.sort((a, b) => a > b ? a : b)[0] }).first();
          if (ohq) {
            const updated = await db.ohq.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now() });
            if (updated) {
              // send update or add sync request to the server
              var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
              const syncResponse = await fetch(`${apiUrl}/vessels/${ohq?.vesselId}/voyages/${ohq?.voyageId}/loadable-studies/${ohq?.loadableStudyId}/ports/${ohq?.portId}/on-hand-quantities/${ohq?.id}`, {
                method: 'POST',
                body: JSON.stringify(ohq),
                headers: headers
              });

              if (syncResponse.status === 200) {
                const sync = await syncResponse.json();
                sync.storeKey = ohq.storeKey;
                sync.type = 'ohq_sync_finished';

                //on success of api call remove all rows of selected primary keys
                primaryKey.forEach(async (primaryKey) => await db.ohq.delete(primaryKey))
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
  async function serverSyncOBQ() {
    //Get all store keys
      await db.obq.orderBy('storeKey').uniqueKeys((storeKeys) => {
        storeKeys.forEach(async (key) => {
          const timeStamp = Date.now - 60000;
          //Get all primary keys with storekey
          const primaryKey = await db.obq.where({ 'storeKey': key }).and(data => !data.timeStamp || data.timeStamp < timeStamp).primaryKeys();

          if (primaryKey?.length) {
            //Get last update record of particular store key
            const obq = await db.obq.where({ ':id': primaryKey.sort((a, b) => a > b ? a : b)[0] }).first();
            if (obq) {
              const updated = await db.obq.where({ 'storeKey': key }).modify({ 'timeStamp': Date.now() });
              if (updated) {
                // send update or add sync request to the server
                var headers = {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
                const syncResponse = await fetch(`${apiUrl}/vessels/${obq?.vesselId}/voyages/${obq?.voyageId}/loadable-studies/${obq?.loadableStudyId}/ports/${obq?.portId}/on-board-quantities/${obq?.id}`, {
                  method: 'POST',
                  body: JSON.stringify(obq),
                  headers: headers
                });

                if (syncResponse.status === 200) {
                  const sync = await syncResponse.json();
                  sync.storeKey = obq.storeKey;
                  sync.type = 'obq_sync_finished';

                  //on success of api call remove all rows of selected primary keys
                  primaryKey.forEach(async (primaryKey) => await db.obq.delete(primaryKey))
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

}());