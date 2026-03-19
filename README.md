# 🔐 Advanced Password Strength Checker

A **Java Swing desktop application** that analyzes the strength of a password in real-time and provides feedback to improve security.

The program evaluates password complexity based on length, character types, and patterns, then displays a strength score, progress bar, and suggestions to help users create stronger passwords.

---

## 📌 Features

- 🔍 Real-time password strength analysis
- 📊 Strength score calculation (0 – 100%)
- 📈 Visual progress bar indicator
- 🔑 Random strong password generator
- 📋 Copy password to clipboard
- 👁 Show / Hide password option
- ✔ Password requirement validation
- 💡 Suggestions for improving weak passwords
- 🎨 Modern GUI with gradient background

---

## 🖥️ Application Interface

The application includes:

- Password input field
- Strength indicator
- Score percentage
- Character counter
- Custom progress bar
- Password requirements display
- Security suggestions
- Generate / Copy / Clear buttons

-------

## ⚙️ Technologies Used

- **Java**
- **Java Swing (GUI)**
- **AWT Graphics**
- **Event Handling**
- **Object-Oriented Programming (OOP)**

---

## 📊 Password Evaluation Criteria

The password strength is calculated based on:

| Criteria | Points |
|--------|--------|
| Password Length | up to 20 |
| Uppercase Letters | +10 |
| Lowercase Letters | +10 |
| Numbers | +10 |
| Special Characters | +20 |
| Length ≥ 12 | +10 |
| Combination of all character types | +10 |
| Consecutive characters penalty | -5 |

Maximum Score: **100**

---------

## 🚀 How to Run

1. Clone the repository

```bash
git clone https://github.com/your-username/password-strength-checker.git
