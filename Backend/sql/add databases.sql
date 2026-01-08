IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'COMP2000')
BEGIN
    EXEC('CREATE SCHEMA COMP2000');
END
GO

DROP TABLE COMP2000.MenuItems
GO

IF NOT EXISTS (SELECT * FROM sys.objects 
               WHERE object_id = OBJECT_ID(N'COMP2000.MenuItems') 
                 AND type in (N'U'))
BEGIN
    CREATE TABLE COMP2000.MenuItems (
        MenuItemID INT PRIMARY KEY IDENTITY(1,1),
        Name VARCHAR(100),
        Description VARCHAR(255),
        Price DECIMAL(5,2),
        Category VARCHAR(50),
        ImageUrl VARCHAR(255)
    );
END
GO


IF NOT EXISTS (SELECT * FROM sys.objects 
               WHERE object_id = OBJECT_ID(N'COMP2000.Reservations') 
                 AND type in (N'U'))
BEGIN
    CREATE TABLE COMP2000.Reservations (
        ReservationID INT PRIMARY KEY IDENTITY(1,1),
        customer_name VARCHAR(100),       
        number_of_people VARCHAR(10),      
        reservation_date VARCHAR(50),    
        reservation_time VARCHAR(50),     
        location VARCHAR(100),           
        CreatedAt DATETIME DEFAULT GETDATE()
    );
END
GO