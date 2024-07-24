package com.mshdabiola.testing

import com.mshdabiola.generalmodel.Content
import com.mshdabiola.generalmodel.Examination
import com.mshdabiola.generalmodel.ExportableData
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Option
import com.mshdabiola.generalmodel.QuestionPlain
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.Subject
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.generalmodel.User
import com.mshdabiola.generalmodel.UserType

val users = listOf(
    User(1, "Alice", UserType.STUDENT, "password1", "path/to/image1.jpg", 85),
    User(2, "Bob", UserType.STUDENT, "password2", "path/to/image2.jpg", 72),
    User(3, "Charlie", UserType.TEACHER, "password3", "path/to/image3.jpg", 0),
    User(4, "David", UserType.STUDENT, "password4", "path/to/image4.jpg", 91),
    User(5, "Emily", UserType.STUDENT, "password5", "path/to/image5.jpg", 68),
    User(6, "Frank", UserType.TEACHER, "password6", "path/to/image6.jpg", 0),
    User(7, "Grace", UserType.STUDENT, "password7", "path/to/image7.jpg", 79),
    User(8, "Henry", UserType.STUDENT, "password8", "path/to/image8.jpg", 83),
    User(9, "Isabella", UserType.STUDENT, "password9", "path/to/image9.jpg", 55),
    User(10, "Jack", UserType.TEACHER, "password10", "path/to/image10.jpg", 0),
)
val series = listOf(
    Series(1, 1, "Mathematics for Beginners"),
    Series(2, 2, "Introduction to Programming"),
    Series(3, 3, "World History 101"),
    Series(4, 1, "Advanced Calculus"),
    Series(5, 4, "Web Development Fundamentals"),
    Series(6, 5, "Art Appreciation"),
    Series(7, 2, "Data Structures and Algorithms"),
    Series(8, 6, "Music Theory for Beginners"),
    Series(9, 7, "Spanish for Travelers"),
    Series(10, 8, "Fitness and Nutrition"),
)
val subjects = listOf(
    Subject(1, 1, "Basic Arithmetic"),
    Subject(2, 1, "Algebra I"),
    Subject(3, 2, "Python Programming"),
    Subject(4, 3, "Ancient Civilizations"),
    Subject(5, 4, "Calculus II"),
    Subject(6, 5, "HTML & CSS"),
    Subject(7, 6, "Painting Techniques"),
    Subject(8, 7, "Data Structures in Java"),
    Subject(9, 8, "Basic Music Theory"),
    Subject(10, 9, "Beginner Spanish Vocabulary"),
)
val topicCategories = listOf(
    TopicCategory(1, "Number Systems", 1),
    TopicCategory(2, "Linear Equations", 2),
    TopicCategory(3, "Variables and Data Types", 3),
    TopicCategory(4, "Mesopotamia", 4),
    TopicCategory(5, "Integration", 5),
    TopicCategory(6, "Web Page Structure", 6),
    TopicCategory(7, "Color Theory", 7),
    TopicCategory(8, "Linked Lists", 8),
    TopicCategory(9, "Scales and Chords", 9),
    TopicCategory(10, "Basic Greetings", 10),
)
val topics = listOf(
    Topic(1, 1, "Integers and Fractions"),
    Topic(2, 2, "Solving Equations with One Variable"),
    Topic(3, 3, "Introduction to Strings"),
    Topic(4, 4, "The Sumerian Civilization"),
    Topic(5, 5, "Definite Integrals"),
    Topic(6, 6, "HTML Tags and Attributes"),
    Topic(7, 7, "Primary and Secondary Colors"),
    Topic(8, 8, "Implementing Linked Lists"),
    Topic(9, 9, "Major and Minor Scales"),
    Topic(10, 10, "Common Phrases in Spanish"),
)
val examinations = listOf(
    Examination(1, 1, 2023, 60),
    Examination(2, 2, 2022, 90),
    Examination(3, 3, 2023, 45),
    Examination(4, 4, 2021, 120),
    Examination(5, 5, 2023, 75),
    Examination(6, 6, 2022, 60),
    Examination(7, 7, 2023, 90),
    Examination(8, 8, 2021, 45),
    Examination(9, 9, 2023, 30),
    Examination(10, 10, 2022, 75),
)
val instructions = listOf(
    Instruction(
        1,
        1,
        "General Instructions",
        listOf(
            Content(content = "Read each question carefully."),
            Content(content = "Time limit: 60 minutes."),
        ),
    ),
    Instruction(
        2,
        2,
        "Multiple Choice Section",
        listOf(
            Content(content = "Choose the best answer for each question."),
            Content(content = "Mark your answers on the answer sheet."),
        ),
    ),
    Instruction(
        3,
        3,
        "Coding Instructions",
        listOf(
            Content(content = "Write your code in the provided space."),
            Content(content = "Use proper syntax and indentation."),
        ),
    ),
    Instruction(
        4,
        4,
        "Essay Instructions",
        listOf(
            Content(content = "Answer the following questions in essay format."),
            Content(content = "Provide clear and concise explanations."),
        ),
    ),
    Instruction(
        5,
        5,
        "Math Exam Instructions",
        listOf(
            Content(content = "Show all your work for full credit."),
            Content(content = "Use a calculator if needed."),
        ),
    ),
    Instruction(
        6,
        6,
        "Web Design Instructions",
        listOf(
            Content(content = "Create a responsive web page."),
            Content(content = "Use HTML, CSS, and JavaScript."),
        ),
    ),
    Instruction(
        7,
        7,
        "Art Project Instructions",
        listOf(
            Content(content = "Use the provided materials to create your artwork."),
            Content(content = "Be creative and express yourself."),
        ),
    ),
    Instruction(
        8,
        8,
        "Music Performance Instructions",
        listOf(
            Content(content = "Prepare a piece of music to perform."),
            Content(content = "Be ready to answer questions about your piece."),
        ),
    ),
    Instruction(
        9,
        9,
        "Language Exam Instructions",
        listOf(
            Content(content = "Answer the questions in the target language."),
            Content(content = "Use proper grammar and vocabulary."),
        ),
    ),
    Instruction(
        10,
        10,
        "Physical Fitness Instructions",
        listOf(
            Content(content = "Warm up before starting the exercises."),
            Content(content = "Follow the instructions for each exercise."),
        ),
    ),
)
val questionsPlain = listOf(
    QuestionPlain(
        1,
        1,
        1,
        "What is the capital of France?",
        "Multiple choice question about capital cities.",
        "Paris",
        0,
        1,
        1,
    ),
    QuestionPlain(
        2,
        2,
        1,
        "Solve for x: 2x + 5 = 11",
        "Math problem involving linear equations.",
        "x = 3",
        1,
        1,
        2,
    ),
    QuestionPlain(
        3,
        1,
        2,
        "Write a Python function to calculate the factorial of a number.",
        "Coding question requiring a Python function.",
        "def factorial(n): ...",
        2,
        2,
        3,
    ),
    QuestionPlain(
        4,
        2,
        2,
        "Explain the difference between a list and a tuple in Python.",
        "Conceptual question about Python data structures.",
        "Answer explaining differences...",
        0,
        2,
        3,
    ),
    QuestionPlain(
        5,
        1,
        3,
        "Describe the major events of the French Revolution.",
        "History question requiring an essay response.",
        "Essay response about French Revolution...",
        3,
        3,
        4,
    ),
    QuestionPlain(
        6,
        1,
        4,
        "Calculate the derivative of f(x) = x^2 + 3x - 2.",
        "Calculus question involving differentiation.",
        "f'(x) = 2x + 3",
        1,
        4,
        5,
    ),
    QuestionPlain(
        7,
        1,
        5,
        "Create a simple HTML page with a heading and a paragraph.",
        "Web development question requiring HTML code.",
        "<html>...</html>",
        2,
        5,
        6,
    ),
    QuestionPlain(
        8,
        1,
        6,
        "What are the primary colors?",
        "Art question about color theory.",
        "Red, Yellow, Blue",
        0,
        6,
        7,
    ),
    QuestionPlain(
        9,
        1,
        7,
        "Explain the concept of a linked list data structure.",
        "Computer science question about data structures.",
        "Explanation of linked lists...",
        0,
        7,
        8,
    ),
    QuestionPlain(
        10,
        1,
        8,
        "What is the key signature of C major?",
        "Music theory question about scales.",
        "No sharps or flats",
        0,
        8,
        9,
    ),
)
val options = listOf(
    // Question 1 Options
    Option(1, 1, 1, "Paris", listOf(Content(content = "The capital of France.")), true),
    Option(2, 2, 1, "Berlin", listOf(Content(content = "The capital of Germany.")), false),
    Option(3, 3, 1, "London", listOf(Content(content = "The capital of England.")), false),
    Option(4, 4, 1, "Rome", listOf(Content(content = "The capital of Italy.")), false),

    // Question 2 Options
    Option(5, 1, 2, "x = 2", listOf(Content(content = "Incorrect solution.")), false),
    Option(6, 2, 2, "x = 3", listOf(Content(content = "Correct solution.")), true),
    Option(7, 3, 2, "x = 4", listOf(Content(content = "Incorrect solution.")), false),
    Option(8, 4, 2, "x = 5", listOf(Content(content = "Incorrect solution.")), false),

    // Question 3 Options (No options for coding questions)

    // Question 4 Options
    Option(
        9,
        1,
        3,
        "Lists are mutable, tuples are immutable.",
        listOf(Content(content = "Part of the difference.")),
        true,
    ),
    Option(
        10,
        2,
        3,
        "Lists use square brackets, tuples use parentheses.",
        listOf(Content(content = "Part of the difference.")),
        true,
    ),
    Option(
        11,
        3,
        3,
        "Lists can contain different data types, tuples cannot.",
        listOf(Content(content = "Incorrect.")),
        false,
    ),
    Option(
        12,
        4,
        3,
        "Lists are ordered, tuples are unordered.",
        listOf(Content(content = "Incorrect.")),
        false,
    ),

    // Question 5 Options (No options for essay questions)

    // Question 6 Options
    Option(13, 1, 4, "f'(x) = 2x + 3", listOf(Content(content = "Correct derivative.")), true),
    Option(
        14,
        2,
        4,
        "f'(x) = x^2 + 3",
        listOf(Content(content = "Incorrect derivative.")),
        false,
    ),
    Option(15, 3, 4, "f'(x) = 2x - 2", listOf(Content(content = "Incorrect derivative.")), false),
    Option(16, 4, 4, "f'(x) = x + 3", listOf(Content(content = "Incorrect derivative.")), false),

    // Question 7 Options (No options for coding questions)

    // Question 8 Options
    Option(
        17,
        1,
        5,
        "Red, Yellow, Blue",
        listOf(Content(content = "Correct primary colors.")),
        true,
    ),
    Option(
        18,
        2,
        5,
        "Green, Orange, Purple",
        listOf(Content(content = "Secondary colors.")),
        false,
    ),
    Option(19, 3, 5, "Black, White, Gray", listOf(Content(content = "Neutral colors.")), false),
    Option(20, 4, 5, "Pink, Brown, Teal", listOf(Content(content = "Tertiary colors.")), false),

    // Question 9 Options (No options for open-ended questions)

    // Question 10 Options
    Option(
        21,
        1,
        6,
        "No sharps or flats",
        listOf(Content(content = "Correct key signature.")),
        true,
    ),
    Option(22, 2, 6, "One sharp (F#)", listOf(Content(content = "Key of G major.")), false),
    Option(23, 3, 6, "Two sharps (F#, C#)", listOf(Content(content = "Key of D major.")), false),
    Option(24, 4, 6, "One flat (Bb)", listOf(Content(content = "Key of F major.")), false),

    // Additional options for more questions (adjust questionId and content accordingly)
    // ... (Add 16 more options to reach a total of 40)
    // ... (Previous 24 options)

// Question 11 Options (Assuming a new question with ID 11)
    Option(
        25,
        1,
        7,
        "Option A for Question 11",
        listOf(Content(content = "Content for Option A")),
        false,
    ),
    Option(
        26,
        2,
        7,
        "Option B for Question 11",
        listOf(Content(content = "Content for Option B")),
        true,
    ),
    Option(
        27,
        3,
        7,
        "Option C for Question 11",
        listOf(Content(content = "Content for Option C")),
        false,
    ),
    Option(
        28,
        4,
        7,
        "Option D for Question 11",
        listOf(Content(content = "Content for Option D")),
        false,
    ),

// Question 12 Options (Assuming a new question with ID 12)
    Option(
        29,
        1,
        8,
        "Option A for Question 12",
        listOf(Content(content = "Content for Option A")),
        true,
    ),
    Option(
        30,
        2,
        8,
        "Option B for Question 12",
        listOf(Content(content = "Content for Option B")),
        false,
    ),
    Option(
        31,
        3,
        8,
        "Option C for Question 12",
        listOf(Content(content = "Content for Option C")),
        false,
    ),
    Option(
        32,
        4,
        8,
        "Option D for Question 12",
        listOf(Content(content = "Content for Option D")),
        false,
    ),

// Question 13 Options (Assuming a new question with ID 13)
    Option(
        33,
        1,
        9,
        "Option A for Question 13",
        listOf(Content(content = "Content for Option A")),
        false,
    ),
    Option(
        34,
        2,
        9,
        "Option B for Question 13",
        listOf(Content(content = "Content for Option B")),
        false,
    ),
    Option(
        35,
        3,
        9,
        "Option C for Question 13",
        listOf(Content(content = "Content for Option C")),
        true,
    ),
    Option(
        36,
        4,
        9,
        "Option D for Question 13",
        listOf(Content(content = "Content for Option D")),
        false,
    ),

// Question 14 Options (Assuming a new question with ID 14)
    Option(
        37,
        1,
        10,
        "Option A for Question 14",
        listOf(Content(content = "Content for Option A")),
        false,
    ),
    Option(
        38,
        2,
        10,
        "Option B for Question 14",
        listOf(Content(content = "Content for Option B")),
        true,
    ),
    Option(
        39,
        3,
        10,
        "Option C for Question 14",
        listOf(Content(content = "Content for Option C")),
        false,
    ),
    Option(
        40,
        4,
        10,
        "Option D for Question 14",
        listOf(Content(content = "Content for Option D")),
        false,
    ),
)

val exportableData = ExportableData(
    users = users,
    series = series,
    subjects = subjects,

    topicCategory = topicCategories,
    topics = topics,

    examinations = examinations,
    instructions = instructions,
    questions = questionsPlain,
    options = options,
)
