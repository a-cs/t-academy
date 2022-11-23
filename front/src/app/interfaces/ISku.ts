export default interface ISku {
    id?: number,
    name?: string,
    category: {
        id: number,
        name?: string
    },
    measurementUnit: {
        id: number,
        description?: string,
        symbol?: string
    }
}