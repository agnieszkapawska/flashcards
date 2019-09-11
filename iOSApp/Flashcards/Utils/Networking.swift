import Foundation
import Combine

enum NetworkingError: Error, Equatable {
    case network(description: String)
    case parsing(description: String)
}

struct Networking {
    func execute<ResponseType: Decodable>(model: Encodable) -> AnyPublisher<ResponseType, NetworkingError> {
        var urlRequest = URLRequest(url: URL(string: "http://localhost:8080/flashcard")!)
        urlRequest.httpMethod = "POST"
        urlRequest.httpBody = model.encode()
        urlRequest.allHTTPHeaderFields = ["Content-Type": "application/json"]
        return URLSession.shared.dataTaskPublisher(for: urlRequest)
                .mapError { error in
                    .network(description: error.localizedDescription)
                }
                .flatMap(maxPublishers: .max(1)) { output in
                     ResponseType.decode(output.data)
                }
                .eraseToAnyPublisher()
    }
}

extension Decodable {
    static func decode(_ data: Data) -> AnyPublisher<Self, NetworkingError> {
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .secondsSince1970
        
        print(String(data: data, encoding: .utf8)!)
        
        return Just(data)
            .decode(type: Self.self, decoder: decoder)
            .mapError { error in
                .parsing(description: error.localizedDescription)
            }
            .eraseToAnyPublisher()
    }
}

extension Encodable {
    func encode() -> Data? {
        return try? JSONEncoder().encode(self)
    }
}

struct Response: Decodable {
    let id: Int
}
