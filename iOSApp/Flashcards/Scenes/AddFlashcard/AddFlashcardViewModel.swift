import Combine
import Foundation

final class AddFlashcardViewModel: Identifiable, ObservableObject {
    
    private let networking: NetworkingProtocol
    private var disposables = Set<AnyCancellable>()
    
    @Published var flashcard = AddFlashcard.Flashcard()
    @Published var tagsList: String = "" {
        didSet {
            flashcard.tagsList = self.tagsList.split(separator: ",")
                                              .map { $0.trimmingCharacters(in: .whitespacesAndNewlines) }
        }
    }
    @Published var presentingAlert = false
    var alertMessage = ""

    
    init(networking: NetworkingProtocol = Networking()) {
        self.networking = networking
    }
    
    func save() {
        let request = AddFlashcard.Request(flashcard)
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
                    receiveValue: { (response: AddFlashcard.Response) in
                        self.alertMessage = "You got it right!"
                        self.presentingAlert = true
                  })
            .store(in: &disposables)
    }
}
