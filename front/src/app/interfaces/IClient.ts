import IAddress from "./IAddress";
import IUser from "./IUser";

export default interface IClient {
    id?: number,
    name?: string,
    cnpj?: string,
    email?: string,
    address: IAddress,
    user?: IUser
}