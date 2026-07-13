package users

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class Display {
    fun show() {
        val textArea = JTextArea().apply {
            isEditable = false
            font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
            margin = Insets(32, 32, 32, 32)
        }
        val scrollPane = JScrollPane(textArea)

        JFrame().apply {
            isVisible = true
            size = Dimension(600, 600)
            isResizable = false

            add(scrollPane)
        }

//        UsersRepository.getInstance("12345").users.registerObserver {
//            textArea.text = it.joinToString("\n")
//        }

        UsersRepository.getInstance("12345").oldestUser.registerObserver {
            textArea.text = "Oldest person is: ${it.firstName} and has ${it.age} years old."
        }


    }
}