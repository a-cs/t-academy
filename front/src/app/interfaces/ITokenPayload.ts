export default interface ITokenPayload {
  exp: number,
  user_name: string,
  user_id: number,
  user_branch_id: number | null,
  authorities: string[],
  jti: string,
  client_id: string,
  scope: string[]
}
