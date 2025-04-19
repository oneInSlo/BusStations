# 🚌 BusTrips – GTFS Bus Arrival Console App

This Java console application processes GTFS (General Transit Feed Specification) data to show upcoming bus arrivals for a specified stop. 
It supports filtering by time, limiting results per bus line, and formatting output in absolute and relative time.

---

## 🚀 Features

- Read GTFS files (`stops.txt`, `trips.txt`, `stop_times.txt`, `routes.txt`)
- Filter bus arrivals for a given stop in the next 2 hours
- Limit results to N upcoming arrivals per bus line
- Output times in either:
    - **Absolute** format (e.g., `12:10, 12:40`)
    - **Relative** format (e.g., `10min, 30min`)
- Safe input validation and memory-conscious data processing
- Clean object-oriented structure (DAO, Service, VAO layers)

---

## 🛠️ How to Run

Compile and run with Java 17+:

```bash
javac BusTrips.java
java BusTrips <stopId> <numberOfBuses> <RELATIVE/ABSOLUTE>
```

---

## 📂 Project Structure

```bash
src/
├── dao/            # Data access classes (file readers)
├── service/        # Business logic (filtering, grouping, formatting)
├── vao/            # Value objects (Trip, Stop, ArrivalTime)
└── BusTrips.java   # Main class
```

---

## 📁 Required Files

```bash
gtfs/
├── stops.txt
├── stop_times.txt
├── trips.txt
└── routes.txt
```

---

## 🧪 Example Output

Absolute time format:
```bash
Postajalisce The Trench Battlefield
6: 12:10, 12:15, 12:40
1: 13:30
```


Relative time format:
```bash
Postajalisce The Trench Battlefield
6: 10 min, 15 min, 40 min
1: 90 min
```


