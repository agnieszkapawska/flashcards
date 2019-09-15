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
        let request = ListFlashcard.Request()
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
                    receiveValue: { (response: [ListFlashcard.Flashcard]) in
                        self.flashcards = response
                  })
            .store(in: &disposables)
    }
}
