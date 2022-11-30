import IAccessLevel from "./IAccessLevel"

export default interface IUser {
    id?: number,
    username: string,
    email: string,
    enabled: boolean,
    accessLevel: IAccessLevel
}
