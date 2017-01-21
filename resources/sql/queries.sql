-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id


-- :name insert-numbers! :! :n
-- :doc insert numbers
INSERT INTO numbers
(val1, val2)
VALUES (:val1, :val2)



-- :name insert-discs :! :n
-- :doc inserts a row to discs TABLE
INSERT INTO discs
(
  type,
  manufacturer,
  color,
  material,
  speed,
  glide,
  stability,
  fade,
  weight,
  name,
  is_lost,
  is_lost_description,
  lost_date,
  is_collection_item,
  is_sold,
  is_broken,
  hole_in_one,
  hole_in_one_date,
  image,
  additional
)
VALUES
(
  :type,
  :manufacturer,
  :color,
  :material,
  :speed,
  :glide,
  :stability,
  :fade,
  :weight,
  :name,
  :is_lost,
  :is_lost_description,
  :lost_date,
  :is_collection_item,
  :is_sold,
  :is_broken,
  :hole_in_one,
  :hole_in_one_date,
  :image,
  :additional
)
