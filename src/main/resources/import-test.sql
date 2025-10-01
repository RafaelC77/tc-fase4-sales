INSERT INTO sales (saleId, buyerCpf, buyerName, buyerEmail, buyerPhone, paymentStatus, carId)
VALUES ('7bc672a8-7296-468a-a305-7bb97058f3b0', '999999999', 'Name', 'teste@mail.com', '999999999', 'PAID', '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO sales_cars (carId, model, brand, car_year, price, status)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Civic', 'Honda', 2020, 1000, 'AVAILABLE');

INSERT INTO sales_cars (carId, model, brand, car_year, price, status)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Corolla', 'Toyota', 2020, 1000, 'SOLD');