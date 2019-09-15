import Foundation
import Combine

enum NetworkingError: Error, Equatable {
    case network(description: String)
    case parsing(description: String)
}

protocol NetworkingProtocol {
    func execute<ResponseType: Decodable>(_ request: AnyRequest<ResponseType>) -> AnyPublisher<ResponseType, NetworkingError>
}

struct Networking: NetworkingProtocol {
    func execute<ResponseType: Decodable>(_ request: AnyRequest<ResponseType>) -> AnyPublisher<ResponseType, NetworkingError> {
        
        var urlRequest = URLRequest(url: request.url)
        urlRequest.httpMethod = request.method
        urlRequest.httpBody = request.body
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
