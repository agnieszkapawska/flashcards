import Combine
import Foundation

final class UpdateFlashcardViewModel: Identifiable, ObservableObject {
    
    private let networking: NetworkingProtocol
    private var disposables = Set<AnyCancellable>()
    private let flashcardId: Int
    
    @Published var flashcard = UpdateFlashcard.Flashcard.empty
    @Published var tagsList: String = "" {
        didSet {
            flashcard.tagsList = self.tagsList.split(separator: ",")
                                              .map { $0.trimmingCharacters(in: .whitespacesAndNewlines) }
        }
    }
    @Published var presentingAlert = false
    var alertMessage = ""
    
    init(flashcardId: Int, networking: NetworkingProtocol = Networking()) {
        self.flashcardId = flashcardId
        self.networking = networking
    }
    
    func load() {
        let request = UpdateFlashcard.Get.Request(flashcardId).anyRequest
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
                        self.flashcard = response
                  })
            .store(in: &disposables)
    }
    
    func save() {
        let request = UpdateFlashcard.Update.Request(flashcard).anyRequest
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
                    receiveValue: { _ in
                        self.alertMessage = "You got it right!"
                        self.presentingAlert = true
                  })
            .store(in: &disposables)
    }
}

extension UpdateFlashcard.Flashcard {
    static var empty: Self {
        return .init(id: 0, question: "", answer: "", exampleUsage: "", explanation: "", tagsList: [])
    }
}
