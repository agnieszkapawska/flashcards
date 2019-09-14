import SwiftUI

struct AddFlashcardView: View {
    @ObservedObject var viewModel: AddFlashcardViewModel
    
    var body: some View {
        NavigationView {
            Form {
                Section {
                    TextField("Question", text: $viewModel.flashcard.question)
                    TextField("Answer", text: $viewModel.flashcard.answer)
                    TextField("Explanation", text: $viewModel.flashcard.explanation)
                    TextField("Example usage", text: $viewModel.flashcard.exampleUsage)
                    TextField("Tags", text: $viewModel.tagsList)
                }
                
                Section { 
                    Button(action: {
                        self.viewModel.save()
                    }) {
                        Text("Add")
                    }
                }
            }.navigationBarTitle("New flashcard")
             .alert(isPresented: $viewModel.presentingAlert) { () -> Alert in
                Alert(title: Text("Message"), message: Text(viewModel.alertMessage), dismissButton: .default(Text("ok")))
             }
        }
    }
}
