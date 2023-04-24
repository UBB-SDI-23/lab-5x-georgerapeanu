export interface ReviewCreate{
    userId: number,
    productId: number,
    score: number,
    comment: string,
    postedDate: Date,
}