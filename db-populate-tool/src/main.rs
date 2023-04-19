use fake::{Dummy, Fake, Faker};
use std::time::{SystemTime, Duration};
use chrono::DateTime;
use chrono::offset::Utc;
use std::fs::File;
use std::io::Write;

const BATCH_SIZE: u32 = 1000;
//const BASE_UNIT: u32 = 1000000;
const BASE_UNIT: u32 = 1000;

const MANUFACTURER_COUNT: u32 = BASE_UNIT;
const PRODUCT_COUNT: u32 = BASE_UNIT;
const USER_COUNT: u32 = BASE_UNIT;
const REVIEWS_COUNT: u32 = 10 * BASE_UNIT;

struct Manufacturer {
    pub description: String,
    pub name: String,
    pub register_date: String
}

trait IntoFileQuery {
    fn to_query(count: u32, fd: &mut File);
}

impl Manufacturer {
    pub fn fake() -> Self {

        let mut result = Self {
            description: fake::faker::lorem::en::Sentence(1..5).fake(),
            name: fake::faker::company::en::CompanyName().fake(),
            register_date: Into::<DateTime<Utc>>::into(SystemTime::UNIX_EPOCH + Duration::from_secs((1681000000..1681239961).fake::<u64>())).format("%Y-%m-%d").to_string()
        };
        result.description = result.description.replace("'", "");
        result.description = result.description.replace("\"", "");
        
        result.name = result.name.replace("'", "");
        result.name = result.name.replace("\"", "");

        return result;
    }
}

impl IntoFileQuery for Manufacturer {
    fn to_query(count: u32, fd: &mut File) {
        write!(fd, "INSERT INTO \"manufacturer\" (\"description\", \"name\", \"register_date\") VALUES ").expect("failure writing");
        for i in 0..count {
            let manufacturer = Manufacturer::fake();
            write!(fd, "\n('{}', '{}', '{}')", manufacturer.description, manufacturer.name, manufacturer.register_date).expect("failure writing");
            if i < count - 1 {
                write!(fd, ",").expect("error writing");
            }
        }
        write!(fd, ";\n").expect("failure writing");
    }
}

struct Product {
    pub description: String,
    pub name: String,
    pub price: f32,
    pub publish_date: String,
    pub weight: u32,
    pub manufacturer_id: u32,
    pub color: String
}

impl Product {
    pub fn fake() -> Self {

        let color: String = (&vec![String::from("RED"), String::from("BLUE"), String::from("GREEN")])[(0..=2).fake::<usize>()].to_string();
        let name: String = (&vec![String::from("watch"), 
                                  String::from("car"), 
                                  String::from("book"),
                                  String::from("table"),
                                  String::from("crayon"),
                                  String::from("rose"),
                                  String::from("glasses"),
                                  String::from("glass"),
                                  String::from("cheese"),
                                  String::from("milk")])[(0..=9).fake::<usize>()].to_string();
        let mut result = Self {
            description: fake::faker::lorem::en::Sentence(1..5).fake(),
            name,
            price: (100..100000).fake::<u32>() as f32 / 100 as f32,
            publish_date: Into::<DateTime<Utc>>::into(SystemTime::UNIX_EPOCH + Duration::from_secs((1681000000..1681239961).fake::<u64>())).format("%Y-%m-%d").to_string(),
            weight: (10..100).fake::<u32>(),
            manufacturer_id: (1..=MANUFACTURER_COUNT / 10).fake::<u32>(),
            color
        };
        result.description = result.description.replace("'", "");
        result.description = result.description.replace("\"", "");
        
        result.name = result.name.replace("'", "");
        result.name = result.name.replace("\"", "");

        return result;
    }
}

impl IntoFileQuery for Product {
    fn to_query(count: u32, fd: &mut File) {
        write!(fd, "INSERT INTO \"product\" (\"description\", \"name\", \"price\", \"publish_date\", \"weight\", \"manufacturer_id\", \"color\") VALUES ").expect("failure writing");
        for i in 0..count {
            let product = Product::fake();
            write!(fd, "\n('{}', '{}', {}, '{}', '{}', {}, '{}')",
                product.description,
                product.name,
                product.price,
                product.publish_date,
                product.weight,
                product.manufacturer_id, 
                product.color
            ).expect("failure writing");
            if i < count - 1 {
                write!(fd, ",").expect("error writing");
            }
        }
        write!(fd, ";\n").expect("failure writing");
    }
}

struct User {
    pub birthday: String,
    pub email: String,
    pub handle: String,
    pub name: String,
    pub registered_at: String,
}

impl User {
    pub fn fake() -> Self {

        let mut result = Self {
            birthday: Into::<DateTime<Utc>>::into(SystemTime::UNIX_EPOCH + Duration::from_secs((1681000000..1681239961).fake::<u64>())).format("%Y-%m-%d").to_string(),
            email: Into::<String>::into(fake::faker::internet::en::SafeEmail().fake::<String>()),
            handle: Into::<String>::into(fake::faker::internet::en::Username().fake::<String>()),
            name: fake::faker::name::en::Name().fake(),
            registered_at: Into::<DateTime<Utc>>::into(SystemTime::UNIX_EPOCH + Duration::from_secs((1681000000..1681239961).fake::<u64>())).format("%Y-%m-%d").to_string(),
        };
        result.name = result.name.replace("'", "");
        result.name = result.name.replace("\"", "");
        
        result.email = result.email.replace("'", "");
        result.email = result.email.replace("\"", "");

        result.handle = result.handle.replace("'", "");
        result.handle = result.handle.replace("\"", "");

        return result;
    }
}

impl IntoFileQuery for User {
    fn to_query(count: u32, fd: &mut File) {
        write!(fd, "INSERT INTO \"user\" (\"birthday\", \"email\", \"handle\", \"name\", \"registered_at\") VALUES ").expect("failure writing");
        for i in 0..count {
            let user = User::fake();
            write!(fd, "\n('{}', '{}', '{}', '{}', '{}')",
                user.birthday,
                user.email,
                user.handle,
                user.name,
                user.registered_at
            ).expect("failure writing");
            if i < count - 1 {
                write!(fd, ",").expect("error writing");
            }
        }
        write!(fd, ";\n").expect("failure writing");
    }
}

struct Review {
    pub comment: String,
    pub posted_date: String,
    pub score: u32,
    pub product_id: u32,
    pub user_id: u32,
}

static mut LAST_PRODUCT_ID: u32 = 1;
static mut LAST_USER_ID: u32 = 1;
impl Review {
    pub fn fake() -> Self {
        let product_id;
        let user_id;
        unsafe{
            product_id = LAST_PRODUCT_ID;
            user_id = LAST_USER_ID;
            LAST_PRODUCT_ID += 1;
            if LAST_PRODUCT_ID > 10000 {
                LAST_PRODUCT_ID = 1;
                LAST_USER_ID += 1;
            }
        }

        let mut result = Self {
            comment: fake::faker::lorem::en::Sentence(1..5).fake(),
            posted_date: Into::<DateTime<Utc>>::into(SystemTime::UNIX_EPOCH + Duration::from_secs((1681000000..1681239961).fake::<u64>())).format("%Y-%m-%d").to_string(),
            score: (1..=5).fake::<u32>(),
            product_id,
            user_id
        };
        result.comment = result.comment.replace("'", "");
        result.comment = result.comment.replace("\"", "");

        return result;
    }
}

impl IntoFileQuery for Review {
    fn to_query(count: u32, fd: &mut File) {
        write!(fd, "INSERT INTO \"review\" (\"comment\", \"posted_date\", \"score\", \"product_id\", \"user_id\") VALUES ").expect("failure writing");
        for i in 0..count {
            let review = Review::fake();
            write!(fd, "\n('{}', '{}', {}, {}, {})",
                review.comment,
                review.posted_date,
                review.score,
                review.product_id,
                review.user_id
            ).expect("failure writing");
            if i < count - 1 {
                write!(fd, ",").expect("error writing");
            }
        }
        write!(fd, ";\n").expect("failure writing");
    }
}


fn main() {
    let mut file = File::create("./schema.sql").expect("failure creating file");

    writeln!(file, "TRUNCATE TABLE \"review\" RESTART IDENTITY CASCADE;").expect("failure writing");
    writeln!(file, "TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE;").expect("failure writing");
    writeln!(file, "TRUNCATE TABLE \"product\" RESTART IDENTITY CASCADE;").expect("failure writing");
    writeln!(file, "TRUNCATE TABLE \"manufacturer\" RESTART IDENTITY CASCADE;").expect("failure writing");

    for i in 0..MANUFACTURER_COUNT / BATCH_SIZE {
        Manufacturer::to_query(BATCH_SIZE, &mut file);
        println!("Manufacturers created {}/{}", i * BATCH_SIZE, MANUFACTURER_COUNT);
    }
    
    for i in 0..PRODUCT_COUNT / BATCH_SIZE {
        Product::to_query(BATCH_SIZE, &mut file);
        println!("Products created {}/{}", i * BATCH_SIZE, PRODUCT_COUNT);
    }
    
    for i in 0..USER_COUNT / BATCH_SIZE {
        User::to_query(BATCH_SIZE, &mut file);
        println!("User created {}/{}", i * BATCH_SIZE, USER_COUNT);
    }
    
    for i in 0..REVIEWS_COUNT / BATCH_SIZE {
        Review::to_query(BATCH_SIZE, &mut file);
        println!("Review created {}/{}", i * BATCH_SIZE, REVIEWS_COUNT);
    }
}
