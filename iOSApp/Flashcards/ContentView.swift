import SwiftUI

struct Entry {
    let word: String
    let translation: String
    let sentence: String
    let definition: String
}

struct ContentView: View {
    @State private var word: String = ""
    @State private var translation: String = ""
    @State private var sentence: String = ""
    
    var body: some View {
        NavigationView {
            Form {
                Section {
                    TextField("Question", text: $word)
                    TextField("Answer", text: $translation)
                    TextField("Example usage", text: $sentence)
                }
                
                Section { 
                    Button(action: {
                        print(self.word)
                    }) {
                        Text("Add")
                    }
                }
            }.navigationBarTitle("Flashcards")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
