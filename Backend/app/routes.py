from fastapi import APIRouter, HTTPException
from .db import cursor, conn

from pydantic import BaseModel
from fastapi import APIRouter, HTTPException
from .db import cursor, conn

router = APIRouter()


class MenuItemCreate(BaseModel):
    name: str
    price: float
    description: str
    category: str
    image_url: str

class ReservationCreate(BaseModel):
    customer_name: str
    number_of_people: int
    reservation_date: str
    reservation_time: str
    location: str

#add
@router.post("/menu/add")
def add_menu_item(menu_item: MenuItemCreate):
    try:
        cursor.execute(
            """
            INSERT INTO COMP2000.MenuItems
            (Name, Price, Description, category, ImageUrl)
            VALUES (?, ?, ?, ?, ?)
            """,
            menu_item.name,
            menu_item.price,
            menu_item.description,
            menu_item.category,
            menu_item.image_url
        )
        conn.commit()
        return {"message": "Menu item added successfully"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

#read
@router.put("/menu/update/{item_id}")
def update_menu_item(item_id: int, menu_item: MenuItemCreate):
    try:
        cursor.execute(
            """
            UPDATE COMP2000.MenuItems
            SET Name = ?,
                Price = ?,
                Description = ?,
                Category = ?,
                ImageUrl = ?
            WHERE MenuItemID = ?
            """,
            (
                menu_item.name,
                menu_item.price,
                menu_item.description,
                menu_item.category,
                menu_item.image_url,
                item_id
            )
        )
        conn.commit()

        if cursor.rowcount == 0:
            raise HTTPException(status_code=404, detail="Menu item not found")

        return {"message": "Menu item updated successfully"}

    except Exception as e:
        print(f"Menu Update Error: {e}")
        raise HTTPException(status_code=400, detail=str(e))

# Read 
@router.get("/menu")
def get_menu_items():
    cursor.execute("SELECT * FROM COMP2000.MenuItems")  
    rows = cursor.fetchall()
    return [dict(zip([column[0] for column in cursor.description], row)) for row in rows]



# Delete 
@router.delete("/menu/delete/{item_id}")
def delete_menu_item(item_id: int):
    try:
        cursor.execute("DELETE FROM COMP2000.MenuItems WHERE MenuItemID=?", item_id)
        conn.commit()
        return {"message": "Menu item deleted successfully"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


#add
# Inside routes.py
@router.post("/reservations/add")
def add_reservation(res: ReservationCreate):
    try:
        cursor.execute(
            """
            INSERT INTO COMP2000.Reservations
            (customer_name, number_of_people, reservation_date, reservation_time, location)
            VALUES (?, ?, ?, ?, ?)
            """,
            (res.customer_name, res.number_of_people, res.reservation_date, res.reservation_time, res.location)
        )
        conn.commit()
        return {"message": "Reservation booked successfully"}
    except Exception as e:
        print(f"Database Error: {e}") # This prints the error to your terminal
        raise HTTPException(status_code=400, detail=str(e))
    

#read
@router.get("/reservations")
def get_reservations():
    cursor.execute("SELECT * FROM COMP2000.Reservations")
    rows = cursor.fetchall()
    return [dict(zip([column[0] for column in cursor.description], row)) for row in rows]

#delete
@router.delete("/reservations/delete/{res_id}")
def delete_reservation(res_id: int):
    try:
        cursor.execute("DELETE FROM COMP2000.Reservations WHERE ReservationID=?", res_id)
        conn.commit()
        return {"message": "Reservation deleted successfully"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
 
@router.put("/reservations/update/{res_id}")
def update_reservation(res_id: int, res: ReservationCreate):
    try:
        cursor.execute(
            """
            UPDATE COMP2000.Reservations
            SET customer_name = ?, 
                number_of_people = ?, 
                reservation_date = ?, 
                reservation_time = ?,
                location = ?
            WHERE ReservationID = ?
            """,
            (res.customer_name, 
             res.number_of_people, 
             res.reservation_date, 
             res.reservation_time, 
             res.location, 
             res_id)
        )
        conn.commit()
        return {"message": "Updated successfully"}
    except Exception as e:
        print(f"Update Error: {e}")
        raise HTTPException(status_code=400, detail=str(e))