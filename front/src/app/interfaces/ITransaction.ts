import IClient from "./IClient";
import ISku from "./ISku";
import IUser from "./IUser";
import IWarehouseSlot from "./IWarehouseSlot";

export default interface ITransaction {
    id: number,
    date: Date,
    quantity: number,
    warehouseSlot: IWarehouseSlot,
    client: IClient,
    sku: ISku,
    user: IUser
}