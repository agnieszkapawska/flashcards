import SwiftUI

struct ListFlashcardsView: View {
    @ObservedObject var viewModel: ListFlashcardsViewModel
    
    var body: some View {
        NavigationView {
            VStack {
                List(viewModel.flashcards) { flashcard in
                    Text(flashcard.question)
                }
            }
            .navigationBarTitle("Flashcards")
            .navigationBarItems(trailing:
                NavigationLink(destination: AddFlashcardView()) {
                    Text("Add")
                }
            )
        }
        .onAppear {
            self.viewModel.load()
        }
        .alert(isPresented: $viewModel.presentingAlert) { () -> Alert in
           Alert(title: Text("Message"),
                 message: Text(viewModel.alertMessage),
                 dismissButton: .default(Text("ok")))
        }
    }
}

struct ListFlashcardsView_Previews: PreviewProvider {
    static var previews: some View {
        ListFlashcardsView(viewModel: ListFlashcardsViewModel())
    }
}
