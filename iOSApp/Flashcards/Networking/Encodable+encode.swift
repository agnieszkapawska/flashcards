import Foundation

extension Encodable {
    func encode() -> Data? {
        return try? JSONEncoder().encode(self)
    }
}
