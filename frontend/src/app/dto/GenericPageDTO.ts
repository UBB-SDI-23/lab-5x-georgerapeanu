export interface GenericPageDTO<T> {
    content: T[],
    totalPages: number,
    pageable: {
        pageNumber: number,
        pageSize: number
    }
}