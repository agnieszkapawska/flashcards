import Foundation
import Combine

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
