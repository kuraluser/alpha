import { Injectable } from "@nestjs/common";
import { Repository, InsertResult, UpdateResult, Brackets } from 'typeorm';

/**
 * Class to perform common db operation and return the results
 */
@Injectable()
export class CommonDBService<T, E> {

    /**
    * Method to insert a single document
    * 
    * @param repository 
    * @param params 
    */
    public async insert(repository: Repository<T>, params: E): Promise<InsertResult> {
        return await repository.insert(params);
    }


    /**
    * Method to update a single document
    * 
    * @param repository 
    * @param params 
    */
    public async updateObject(repository: Repository<T>, updateObj: E): Promise<E> {
        return await repository.save(updateObj);
    }

    /**
     * Method to update fields of a single document
     * 
     * @param repository 
     * @param params 
     * @param updateData 
     */
    public async updateData(repository: Repository<T>, params: any, updateData: any): Promise<UpdateResult> {
        return await repository.update(params, updateData);
    }


    /**
    * Method to return an array of documents based on the params
    * 
    * @param repository 
    * @param params 
    */
    public async find(repository: Repository<T>, alias: string, conditions: string, params: any, orConditions?: string, orParams?: any): Promise<E[]> {
        if (orConditions) {
            return await repository.createQueryBuilder(alias).where(conditions, params).andWhere(orConditions, orParams).getMany() as any;
        }
        return await repository.createQueryBuilder(alias).where(conditions, params).getMany() as any;
    }

    /**
     * Method to return a single document based on the params
     * 
     * @param repository 
     * @param params 
     */
    public async findOne(repository: Repository<T>, alias: string, conditions: string, params: any, orConditions?: string, orParams?: any): Promise<E> {
        if (orConditions) {
            return await repository.createQueryBuilder(alias).where(conditions, params).andWhere(orConditions, orParams).getOne() as any;
        }
        return await repository.createQueryBuilder(alias).where(conditions, params).getOne() as any;
    }


    /**
     * Method to return docs sorted and after applying limit
     * 
     * @param repository 
     * @param params 
     * @param sort 
     * @param limit 
     */
    public async findAndSortWithLimit(repository: Repository<T>, alias: string, conditions: string, params: any, sort: string, limit: number, orConditions?: string, orParams?: any): Promise<E[]> {
        if (orConditions) {
            return await repository.createQueryBuilder(alias).where(conditions, params).andWhere(orConditions, orParams).orderBy(sort, "ASC").limit(limit).getMany() as any;
        }
        return await repository.createQueryBuilder(alias).where(conditions, params).orderBy(sort, "ASC").limit(limit).printSql().getMany() as any;
    }
}