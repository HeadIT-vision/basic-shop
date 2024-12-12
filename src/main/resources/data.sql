
INSERT INTO categories (category_name) VALUES
                                           ('소설'),
                                           ('역사'),
                                           ('철학'),
                                           ('과학'),
                                           ('수필'),
                                           ('경제'),
                                           ('자기계발');

INSERT INTO products (
    product_name,
    publisher_name,
    author_name,
    translator_name,
    purchase_price,
    unit_price,
    discount_price,
    selling_price,
    description,
    thumbnail_image_data,
    product_image_data,
    product_status,
    category_id
) VALUES
      ('Java Programming Basics', 'TechBooks', 'John Doe', 'Jane Smith', 20000, 25000, 2000, 23000, 'A beginner-friendly guide to Java programming.', 'thumbnail1.jpg', 'image1.jpg', 'INACTIVE', 1),
      ('Advanced Python', 'CodeMasters', 'Alice Brown', NULL, 30000, 35000, 5000, 30000, 'An advanced-level book on Python programming.', 'thumbnail2.jpg', 'image2.jpg', 'INACTIVE', 1),
      ('Web Development with HTML', 'WebBooks', 'Bob White', NULL, 15000, 20000, 3000, 17000, 'A comprehensive guide to web development using HTML.', 'thumbnail3.jpg', 'image3.jpg', 'ACTIVE', 2),
      ('Effective JavaScript', 'DevLibrary', 'Chris Green', NULL, 25000, 30000, 4000, 26000, 'Learn JavaScript effectively with this practical guide.', 'thumbnail4.jpg', 'image4.jpg', 'ACTIVE', 3),
      ('Database Design Principles', 'DataPress', 'David Black', NULL, 35000, 40000, 6000, 34000, 'A deep dive into database design and best practices.', 'thumbnail5.jpg', 'image5.jpg', 'ACTIVE', 2);
