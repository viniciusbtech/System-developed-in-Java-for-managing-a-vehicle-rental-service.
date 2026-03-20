🚗 Vehicle Rental System

System developed in Java for managing a vehicle rental service.
The project uses Object-Oriented Programming (OOP) concepts and layered organization with the DAO (Data Access Object) pattern.

📌 Features

The system allows:

Customer registration
Vehicle registration
Vehicle rental
Control of rented vehicles
Availability checking
Custom exception handling
Different types of vehicles

🧱 Project Structure

src/viniciusNunes_locadora/
Main classes:

🚗 Vehicles

Veiculo → base class
Carro
Moto
Caminhao
Onibus
Uti

👤 Customers

Cliente
📄 Rental

Aluguel
🗂 DAO (Data Access)

DAOCliente
DAOVeiculo
DAOAluguel

⚙ Main System

Locadora
MinhaLocadora
❗ Custom Exceptions
ClienteInexistente
ClienteJaCadastrado
VeiculoInexistente
VeiculoJaAlugado
VeiculoNaoAlugado
VeiculoJaCadastrado
DadosInvalidos

🔗 Connection

Conexao

🧪 Tests

VeiculoJaCadastradoTest
🛠 Technologies Used

Java

Object-Oriented Programming (OOP)
Eclipse / VS Code
DAO Structure

▶ How to Run the Project

Clone the repository
git clone https://github.com/seuusuario/locadora-java.git
Navigate to the project folder
cd locadora-java
Compile the files
javac src/viniciusNunes_locadora/*.java
Run the program
java viniciusNunes_locadora.MinhaLocadora

📚 Concepts Applied

This project uses several important programming concepts:
Object-Oriented Programming
Inheritance
Polymorphism
Encapsulation
Exception Handling
DAO Design Pattern
Package Organization

🎯 Objective
The objective of this project is to simulate the operation of a vehicle rental system, allowing the management of customers, vehicles, and rentals in an organized way using good Java programming practices.

👨‍💻 Author
Vinicius Nunes
Project developed for academic purposes.
