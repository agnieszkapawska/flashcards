INSERT INTO USER (id, user_name, password) VALUES
(1, 'Kasia', 'pass'),
(2, 'Aga', 'zzz'),
(3, 'Marta', 'mmm');

INSERT INTO FLASHCARDS_TO_LEARN (id, user_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO FLASHCARD(id, answer, correct_answer_counter, example_usage, explanation, question, user_id, flashcards_to_learn_id) VALUES
(1, 'home', 2, 'at home', 'place where you live', 'dom', 1, 1),
(2, 'cat', 2, 'happy cat', 'sweet animal', 'kot', 1, 1),
(3, 'air', 0, 'fell the air', 'you breathe because of it', 'powietrze', 2, 2),
(4, 'computer', 0, 'computer sickness', 'programmers working using it', 'komputer', 2, 2),
(5, 'carrot', 0, 'good carrot', 'vegatable', 'marchewka', 3, 3);