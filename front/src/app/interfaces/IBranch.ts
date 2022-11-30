import IAddress from "./IAddress";

export default interface IBranch {
    id?: number,
    name?: string,
    max_rows?: number,
    max_columns?: number,

    Address?: IAddress
}