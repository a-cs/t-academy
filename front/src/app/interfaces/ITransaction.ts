import IClient from "./IClient";
import ISku from "./ISku";
import IUser from "./IUser";
import IWarehouseSlot from "./IWarehouseSlot";

export default interface ITransaction {
    id: number,
    date: string,
    quantity: number,
    warehouseSlot: IWarehouseSlot,
    client: IClient,
    sku: ISku,
    user: IUser,
    type: string
}