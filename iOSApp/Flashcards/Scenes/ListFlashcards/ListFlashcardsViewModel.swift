import Combine
import Foundation

final class ListFlashcardsViewModel: Identifiable, ObservableObject {
    
    private let networking: NetworkingProtocol
    private var disposables = Set<AnyCancellable>()
    
    @Published var flashcards: [ListFlashcard.Flashcard] = []
    @Published var presentingAlert = false
    var alertMessage = ""

    init(networking: NetworkingProtocol = Networking()) {
        self.networking = networking
    }
    
    func load() {
        let request = ListFlashcard.Request().anyRequest
        networking.execute(request)
            .receive(on: DispatchQueue.main)
            .sink(
                    receiveCompletion: { value in
                      switch value {
                      case .failure(let error):
                        self.alertMessage = error.localizedDescription
                        self.presentingAlert = true
                      case .finished:
                        break
                      }
                    },
                    receiveValue: { response in
                        self.flashcards = response
                  })
            .store(in: &disposables)
    }
}

extension ListFlashcard.Flashcard {
    static var stub: [Self] {
        return [
        ListFlashcard.Flashcard(id: 0, question: "Krzyś"),
        ListFlashcard.Flashcard(id: 1, question: "Agusia"),
        ListFlashcard.Flashcard(id: 2, question: "Rudnik"),
        ListFlashcard.Flashcard(id: 3, question: "Pierdziusia")
        ]
    }
}
