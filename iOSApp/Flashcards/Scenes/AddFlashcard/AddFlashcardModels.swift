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
        let url = URL(string: "http://localhost:8080/flashcard")!
        let body: Data?
        
        init(_ model: Flashcard) {
            body = model.encode()
        }
    }
    
    struct Response: Decodable {
        let id: Int
    }
}

