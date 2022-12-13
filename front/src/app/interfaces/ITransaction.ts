import IClient from "./IClient";
import ISku from "./ISku";
import IUser from "./IUser";
import IWarehouseSlot from "./IWarehouseSlot";

export default interface ITransaction {
    id: number,
    date: string,
    quantity: number,
    warehouseSlot: string,
    client: string,
    sku: string,
    user: string,
    type: string,
    branch: string,
}