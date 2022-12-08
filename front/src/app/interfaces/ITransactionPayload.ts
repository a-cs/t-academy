import IClient from "./IClient";
import ISku from "./ISku";

export default interface ITransactionPayload {
  user: {
    id: number
  },
  type: string,
  client: {
    id: number
  }
  sku: {
    id: number
  },
  quantity: number,
  warehouseSlot: {
    warehouseSlotId: {
      branch: {
        id: number | null
      }
    }
  }

  // user: {
  //   id: number
  // },
  // type: string,
  // client: IClient,
  // sku: ISku,
  // quantity: number,
  // warehouseSlot: {
  //   warehouseSlotId: {
  //     branch: {
  //       id: number | null
  //     }
  //   }
  // }
}
