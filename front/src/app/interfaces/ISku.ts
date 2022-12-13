import ICategory from "./ICategory"
import IMeasurementUnit from "./IMeasurementUnit"

export default interface ISku {
    id?: number,
    name: string,
    category: ICategory,
    measurementUnit: IMeasurementUnit
}
