import Combine

struct Flashcard {
    var question: String = ""
    var answer: String = ""
    var exampleUsage: String = ""
    var explanation: String = ""
}

final class AddFlashcardViewModel: Identifiable, ObservableObject {
    
    @Published
    var flashcard = Flashcard()
     
    func save() {
        print("\(flashcard.question)  \(flashcard.answer)  \(flashcard.exampleUsage)  \(flashcard.explanation)")
    }
}

import Foundation

enum NetworkingError: Error {
    case network(description: String)
    case parsing(description: String)
}

struct Networking {
    func execute<Model: Decodable>() -> AnyPublisher<Model, NetworkingError> {
        let url = URL(string: "whaever")!
        return URLSession.shared.dataTaskPublisher(for: url)
                .mapError { error in
                    .network(description: error.localizedDescription)
                }
                .flatMap(maxPublishers: .max(1)) { output in
                    Model.decode(output.data)
                }
                .eraseToAnyPublisher()
    }
}

extension Decodable {
    static func decode(_ data: Data) -> AnyPublisher<Self, NetworkingError> {
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .secondsSince1970
        
        return Just(data)
            .decode(type: Self.self, decoder: decoder)
          .mapError { error in
            .parsing(description: error.localizedDescription)
          }
          .eraseToAnyPublisher()
    }
}
