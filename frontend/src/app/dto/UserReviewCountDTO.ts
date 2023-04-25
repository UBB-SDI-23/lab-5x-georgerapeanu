import { User } from "../model/User"

export default interface UserReviewCountDTO {
    userDTO: User,
    count: number
}