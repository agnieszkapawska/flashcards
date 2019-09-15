import Foundation

enum UpdateFlashcard {
    
    struct Flashcard: Codable {
        let id: Int
        var question: String
        var answer: String
        var exampleUsage: String
        var explanation: String
        var tagsList: [String]
    }
    
    enum Get {

        struct Request: GetRequest {
            let url: URL
            
            init(_ flashcardId: Int) {
                url = URL(string: "http://localhost:8080/flashcards/\(flashcardId)")!
            }
        }
    }
    
    enum Update {

        struct Request: PostRequest {
            let url: URL
            let body: Data?
            
            init(_ flashcard: Flashcard) {
                url = URL(string: "http://localhost:8080/flashcards/\(flashcard.id)")!
                body = flashcard.encode()
            }
        }
        
        struct Response: Decodable {
            let id: Int
        }
        
    }
}

