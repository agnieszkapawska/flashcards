import Foundation

protocol Request {
    var url: URL { get }
    var body: Data? { get }
    var method: String { get }
}

// MARK: - PostRequest

protocol PostRequest: Request {
}

extension PostRequest {
    var method: String { return "POST" }
}

// MARK: - GetRequest

protocol GetRequest: Request {
}

extension GetRequest {
    var method: String { return "GET" }
}
