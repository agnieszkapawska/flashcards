import Foundation

enum AddFlashcard {
    
    struct Flashcard: Encodable {
        var question: String = ""
        var answer: String = ""
        var exampleUsage: String = ""
        var explanation: String = ""
        var tagsList: [String] = []
    }

    struct Request: PostRequest {
        typealias ResponseType = Response
        let url = URL(string: "http://localhost:8080/flashcards")!
        let body: Data?
        
        init(_ model: Flashcard) {
            body = model.encode()
        }
    }
    
    struct Response: Decodable {
        let id: Int
    }
}

