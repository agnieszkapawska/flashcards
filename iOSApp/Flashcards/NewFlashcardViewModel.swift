import Combine
import Foundation

struct Flashcard: Encodable {
    var question: String = ""
    var answer: String = ""
    var exampleUsage: String = ""
    var explanation: String = ""
}

final class NewFlashcardViewModel: Identifiable, ObservableObject {
    
    @Published var flashcard = Flashcard()
    @Published var presentingAlert = false
    var alertMessage = ""

    private var disposables = Set<AnyCancellable>()
    
    func save() {
        Networking().execute(model: flashcard as Encodable)
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
                    receiveValue: { (response: Response) in
                        print(response.id)
                        self.alertMessage = "You got it right!"
                        self.presentingAlert = true
                  })
            .store(in: &disposables)
    }
}
