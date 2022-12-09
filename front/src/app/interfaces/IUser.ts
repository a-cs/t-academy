import IAccessLevel from "./IAccessLevel"
import IBranch from "./IBranch"

export default interface IUser {
    id?: number,
    username: string,
    email: string,
    enabled: boolean,
    accessLevel: IAccessLevel,
    branch?: IBranch
}
