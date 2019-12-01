import Foundation

protocol Request {
    associatedtype ResponseType: Decodable
    var url: URL { get }
    var body: Data? { get }
    var method: String { get }
}

extension Request {
    var anyRequest: AnyRequest<ResponseType> {
        return AnyRequest(self)
    }
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
    var body: Data? { return nil }
}

// MARK - AnyRequest

struct AnyRequest<T: Decodable>: Request {
    typealias ResponseType = T
    
    let url: URL
    let body: Data?
    let method: String
    
    init<U: Request>(_ request: U) where U.ResponseType == T {
        url = request.url
        body = request.body
        method = request.method
    }
}
