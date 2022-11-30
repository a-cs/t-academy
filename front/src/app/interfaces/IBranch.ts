import IAddress from "./IAddress";

export default interface IBranch {
    id?: number,
    name: string,
    maxRows: number,
    maxColumns: number,
    address: IAddress
}