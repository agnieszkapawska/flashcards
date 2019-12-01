import Foundation

enum ListFlashcard {
    
    struct Flashcard: Decodable, Identifiable {
        let id: Int
        let question: String
    }

    struct Request: GetRequest {
        typealias ResponseType = [Flashcard]
        let url = URL(string: "http://localhost:8080/flashcards")!
    }
}

