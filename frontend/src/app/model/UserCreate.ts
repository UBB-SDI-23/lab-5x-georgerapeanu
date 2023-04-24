export interface UserCreate {
    id: number;
    name: string;
    handle: string;
    email: string;
    birthday: Date;
    registeredAt: Date;
}