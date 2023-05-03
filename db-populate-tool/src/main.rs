use pwhash::bcrypt;
use chrono::offset::Utc;
use chrono::DateTime;
use fake::Fake;
use std::fs::File;
use std::io::Write;
use std::time::{Duration, SystemTime};

const BATCH_SIZE: usize = 1000;
const USER_COUNT: usize = 10000;
const MANUFACTURER_COUNT: usize = 1000000;
const PRODUCT_COUNT: usize = 1000000;
const REVIEW_COUNT: usize = 10000000;

struct User {
    pub handle: String,
    pub password: String,
    pub activated: bool
}

struct UserProfile {
    pub birthday: String,
    pub email: String,
    pub handle: String,
    pub name: String,
    pub registered_at: String,
}

struct Manufacturer {
    pub description: String,
    pub name: String,
    pub register_date: String,
    pub user_handle: String,
}

struct Product {
    pub description: String,
    pub name: String,
    pub price: f32,
    pub publish_date: String,
    pub weight: u32,
    pub manufacturer_id: usize,
    pub color: String,
}

struct Review {
    pub comment: String,
    pub posted_date: String,
    pub score: u32,
    pub product_id: u32,
    pub user_handle: String,
}

fn generate_user_handles(total: usize) -> Vec<String> {
    let mut answer: Vec<String> = vec![];
    for i in 0..total {
        let handle: String =
            fake::faker::internet::en::Username().fake::<String>() + &i.to_string();
        answer.push(handle.replace("'", "").replace("\"", ""));
    }
    return answer;
}

fn fake_users(total: usize, batch: usize, user_handles: &Vec<String>, fd: &mut File) {
    assert!(total % batch == 0);
    assert!(total == user_handles.len());

    let password =
        bcrypt::hash_with(
            bcrypt::BcryptSetup {
                variant: Some(bcrypt::BcryptVariant::V2a),
                ..Default::default()
            },
            "password" 
        ).expect("failed encrypting password");

    for i in 0..total {
        if i % batch == 0 {
            println!("Faked users {}/{}", i, total);
            write!(
                fd,
                "INSERT INTO \"user\" (\"handle\", \"password\", \"activated\") VALUES "
            )
            .expect("failure writing");
        }

        let user = User {
            handle: user_handles[i].clone(),
            password: password.clone(),
            activated: true
        };

        write!(fd, "('{}', '{}', {})", user.handle, user.password, if user.activated {"true"} else {"false"}).expect("error writing an entity");

        if (i + 1) % batch == 0 {
            write!(fd, ";\n").expect("failure writing");
        } else {
            write!(fd, ",\n").expect("failure writing");
        }
    }
}

fn fake_user_profiles(total: usize, batch: usize, user_handles: &Vec<String>, fd: &mut File) {
    assert!(total % batch == 0);
    assert!(total == user_handles.len());

    for i in 0..total {
        if i % batch == 0 {
            println!("Faked user profiles {}/{}", i, total);
            write!(fd, "INSERT INTO \"user_profile\" (\"birthday\", \"email\", \"handle\", \"name\", \"registered_at\") VALUES ").expect("failure writing");
        }

        let user_profile = UserProfile {
            handle: user_handles[i].clone(),
            name: fake::faker::name::en::Name()
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            birthday: DateTime::<Utc>::from(
                SystemTime::UNIX_EPOCH
                    + Duration::from_secs((1681000000..1681239961).fake::<u64>()),
            )
            .format("%Y-%m-%d")
            .to_string(),
            email: fake::faker::internet::en::SafeEmail()
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            registered_at: DateTime::<Utc>::from(
                SystemTime::UNIX_EPOCH
                    + Duration::from_secs((1681000000..1681239961).fake::<u64>()),
            )
            .format("%Y-%m-%d")
            .to_string(),
        };
        write!(
            fd,
            "\n('{}', '{}', '{}', '{}', '{}')",
            user_profile.birthday,
            user_profile.email,
            user_profile.handle,
            user_profile.name,
            user_profile.registered_at
        )
        .expect("failure writing");

        if (i + 1) % batch == 0 {
            write!(fd, ";\n").expect("failure writing");
        } else {
            write!(fd, ",\n").expect("failure writing");
        }
    }
}

fn fake_manufacturers(total: usize, batch: usize, user_handles: &Vec<String>, fd: &mut File) {
    assert!(total % batch == 0);

    for i in 0..total {
        if i % batch == 0 {
            println!("Faked manufacturers {}/{}", i, total);
            write!(fd, "INSERT INTO \"manufacturer\" (\"description\", \"name\", \"register_date\", \"user_handle\") VALUES ").expect("failure writing");
        }

        let manufacturer = Manufacturer {
            description: fake::faker::lorem::en::Sentence(1..5)
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            name: fake::faker::company::en::CompanyName()
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            register_date: DateTime::<Utc>::from(
                SystemTime::UNIX_EPOCH
                    + Duration::from_secs((1681000000..1681239961).fake::<u64>()),
            )
            .format("%Y-%m-%d")
            .to_string(),
            user_handle: user_handles[(0..user_handles.len()).fake::<usize>()].clone(),
        };

        write!(
            fd,
            "\n('{}', '{}', '{}', '{}')",
            manufacturer.description,
            manufacturer.name,
            manufacturer.register_date,
            manufacturer.user_handle
        )
        .expect("failure writing");

        if (i + 1) % batch == 0 {
            write!(fd, ";\n").expect("failure writing");
        } else {
            write!(fd, ",\n").expect("failure writing");
        }
    }
}

fn fake_products(total: usize, batch: usize, fd: &mut File) {
    assert!(total % batch == 0);

    for i in 0..total {
        if i % batch == 0 {
            println!("Faked products {}/{}", i, total);
            write!(fd, "INSERT INTO \"product\" (\"description\", \"name\", \"price\", \"publish_date\", \"weight\", \"manufacturer_id\", \"color\") VALUES ").expect("failure writing");
        }

        let name: String = (&vec![
            String::from("watch"),
            String::from("car"),
            String::from("book"),
            String::from("table"),
            String::from("crayon"),
            String::from("rose"),
            String::from("glasses"),
            String::from("glass"),
            String::from("cheese"),
            String::from("milk"),
        ])[(0..=9).fake::<usize>()]
        .to_string();
        let color: String = (&vec![
            String::from("RED"),
            String::from("BLUE"),
            String::from("GREEN"),
        ])[(0..=2).fake::<usize>()]
        .to_string();

        let product = Product {
            description: fake::faker::lorem::en::Sentence(1..5)
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            name,
            price: (100..100000).fake::<u32>() as f32 / 100 as f32,
            publish_date: DateTime::<Utc>::from(
                SystemTime::UNIX_EPOCH
                    + Duration::from_secs((1681000000..1681239961).fake::<u64>()),
            )
            .format("%Y-%m-%d")
            .to_string(),
            weight: (10..100).fake::<u32>(),
            manufacturer_id: (1..=MANUFACTURER_COUNT).fake::<usize>(),
            color,
        };

        write!(
            fd,
            "\n('{}', '{}', {}, '{}', '{}', {}, '{}')",
            product.description,
            product.name,
            product.price,
            product.publish_date,
            product.weight,
            product.manufacturer_id,
            product.color
        )
        .expect("failure writing");

        if (i + 1) % batch == 0 {
            write!(fd, ";\n").expect("failure writing");
        } else {
            write!(fd, ",\n").expect("failure writing");
        }
    }
}

fn fake_reviews(total: usize, batch: usize, user_handles: &Vec<String>, fd: &mut File) {
    assert!(total % batch == 0);

    let mut user_id = 1;
    let mut product_id = 1;

    for i in 0..total {
        if i % batch == 0 {
            println!("Faked reviews {}/{}", i, total);
            write!(fd, "INSERT INTO \"review\" (\"comment\", \"posted_date\", \"score\", \"product_id\", \"user_handle\") VALUES ").expect("failure writing");
        }

        let review = Review {
            comment: fake::faker::lorem::en::Sentence(1..5)
                .fake::<String>()
                .replace("'", "")
                .replace("\"", ""),
            posted_date: DateTime::<Utc>::from(
                SystemTime::UNIX_EPOCH
                    + Duration::from_secs((1681000000..1681239961).fake::<u64>()),
            )
            .to_string(),
            score: (1..=5).fake::<u32>(),
            product_id,
            user_handle: user_handles[user_id - 1].clone(),
        };

        write!(
            fd,
            "\n('{}', '{}', {}, {}, '{}')",
            review.comment, review.posted_date, review.score, review.product_id, review.user_handle
        )
        .expect("failure writing");

        user_id += 1;

        if user_id > USER_COUNT {
            user_id = 1;
            product_id += 1;
        }

        if (i + 1) % batch == 0 {
            write!(fd, ";\n").expect("failure writing");
        } else {
            write!(fd, ",\n").expect("failure writing");
        }
    }
}

fn main() {
    let mut file = File::create("./schema.sql").expect("failure creating file");

    writeln!(file, "TRUNCATE TABLE \"review\" RESTART IDENTITY CASCADE;").expect("failure writing");
    writeln!(
        file,
        "TRUNCATE TABLE \"user_profile\" RESTART IDENTITY CASCADE;"
    )
    .expect("failure writing");
    writeln!(file, "TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE;").expect("failure writing");
    writeln!(file, "TRUNCATE TABLE \"product\" RESTART IDENTITY CASCADE;")
        .expect("failure writing");
    writeln!(
        file,
        "TRUNCATE TABLE \"manufacturer\" RESTART IDENTITY CASCADE;"
    )
    .expect("failure writing");

    let user_handles = generate_user_handles(USER_COUNT);

    fake_users(USER_COUNT, BATCH_SIZE, &user_handles, &mut file);
    fake_user_profiles(USER_COUNT, BATCH_SIZE, &user_handles, &mut file);
    fake_manufacturers(MANUFACTURER_COUNT, BATCH_SIZE, &user_handles, &mut file);
    fake_products(PRODUCT_COUNT, BATCH_SIZE, &mut file);
    fake_reviews(REVIEW_COUNT, BATCH_SIZE, &user_handles, &mut file);
}
