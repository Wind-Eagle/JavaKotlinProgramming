import java.awt.EventQueue
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class MainWindow(title: String) : JFrame() {
    private val str = StringBuilder()
    private val txtres = JLabel()
    private val txt = JTextField()
    private val vlist = DefaultListModel<String>()
    private val vmap: MutableMap<String, Double> = HashMap<String, Double>()
    var expr: Expression? = null
    val parser = ParserImpl()

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        setTitle("Jeffset <3")
        val closeBtn = JButton("Close")
        closeBtn.addActionListener { System.exit(0) }
        createLayout(closeBtn)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 200)
        setLocationRelativeTo(null)
    }

    private fun CalculateExpression() {
        str.clear()
        str.append(txt.text)
        try {
            for ((i, j) in vmap) {
                parser.addValue(i, j)
            }
            expr = parser.parseExpression(str.toString());
            txtres.setText(expr!!.accept(ComputeExpressionVisitor()).toString())
        } catch (ex: Exception) {
            txtres.setText("")
        }
    }

    private fun AddSymbol(s: Char) {
        str.clear()
        str.append(txt.text)
        str.append(s)
        txt.text = str.toString()
    }

    private fun AddSymbols(s: String) {
        for (i in s) {
            AddSymbol(i)
        }
    }

    private fun AddToList() {
        val result =
            JOptionPane.showInputDialog(
                "Enter a variable in a format: val=x"
            ).filter { !it.isWhitespace() }
                .split("=")
        if (result.size == 2) {
            if (result[0] == "abs") {
                JOptionPane.showMessageDialog(this, "Incorrect format!")
                return
            }
            for (i in result[0]) {
                if (i < 'a' || i > 'z') {
                    JOptionPane.showMessageDialog(this, "Incorrect format!")
                    return
                }
            }
            try {
                vmap[result[0]] = result[1].toDouble()
                RemakeList()
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(this, "Incorrect format!")
                return
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect format!")
            return
        }
    }

    private fun ClearList() {
        vmap.clear()
        RemakeList()
    }

    private fun RemakeList() {
        vlist.clear()
        for ((i, j) in vmap) {
            vlist.addElement(i.toString() + "=" + j.toString())
        }
    }

    private fun GetInfo() {
        str.clear()
        str.append(txt.text)
        try {
            for ((i, j) in vmap) {
                parser.addValue(i, j)
            }
            expr = parser.parseExpression(str.toString());
        } catch (ex: Exception) {
            txtres.setText("")
        }
        var strRes = ""
        try {
            strRes = expr!!.accept(DebugRepresentationExpressionVisitor()) as String
        } catch (ex: Exception) {
            JOptionPane.showMessageDialog(
                this,
                "An exception occured! Make sure that the expression is correct and you have entered values for all variables."
            )
            return
        }
        JOptionPane.showMessageDialog(this, strRes)
    }

    private fun createLayout(vararg arg: JComponent) {
        val bt0 = JButton("0")
        bt0.addActionListener { AddSymbol('0') }
        val bt1 = JButton("1")
        bt1.addActionListener { AddSymbol('1') }
        val bt2 = JButton("2")
        bt2.addActionListener { AddSymbol('2') }
        val bt3 = JButton("3")
        bt3.addActionListener { AddSymbol('3') }
        val bt4 = JButton("4")
        bt4.addActionListener { AddSymbol('4') }
        val bt5 = JButton("5")
        bt5.addActionListener { AddSymbol('5') }
        val bt6 = JButton("6")
        bt6.addActionListener { AddSymbol('6') }
        val bt7 = JButton("7")
        bt7.addActionListener { AddSymbol('7') }
        val bt8 = JButton("8")
        bt8.addActionListener { AddSymbol('8') }
        val bt9 = JButton("9")
        bt9.addActionListener { AddSymbol('9') }
        val bteq = JButton("=")
        bteq.addActionListener { CalculateExpression() }
        val btadd = JButton("+")
        btadd.addActionListener { AddSymbol('+') }
        val btsub = JButton("-")
        btsub.addActionListener { AddSymbol('-') }
        val btmul = JButton("*")
        btmul.addActionListener { AddSymbol('*') }
        val btdiv = JButton("/")
        btdiv.addActionListener { AddSymbol('/') }
        val btopen = JButton("(")
        btopen.addActionListener { AddSymbol('(') }
        val btclose = JButton(")")
        btclose.addActionListener { AddSymbol(')') }
        val btabs = JButton("abs")
        btabs.addActionListener { AddSymbols("abs()") }
        val btaddlist = JButton("add")
        btaddlist.addActionListener { AddToList() }
        val btclearlist = JButton("clear")
        btclearlist.addActionListener { ClearList() }
        val btinfo = JButton("info")
        btinfo.addActionListener { GetInfo() }
        val gl = GridBagLayout()
        val c = GridBagConstraints()
        contentPane.layout = gl
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 2
        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.weighty = 1.0
        contentPane.add(txt, c)
        c.gridx = 2
        c.gridwidth = 1
        contentPane.add(txtres, c)
        c.gridwidth = 1
        c.gridx = 0
        c.gridy = 1
        contentPane.add(bt1, c)
        c.gridx = 1
        contentPane.add(bt2, c)
        c.gridx = 2
        contentPane.add(bt3, c)
        c.gridx = 0
        c.gridy = 2
        contentPane.add(bt4, c)
        c.gridx = 1
        contentPane.add(bt5, c)
        c.gridx = 2
        contentPane.add(bt6, c)
        c.gridx = 0
        c.gridy = 3
        contentPane.add(bt7, c)
        c.gridx = 1
        contentPane.add(bt8, c)
        c.gridx = 2
        contentPane.add(bt9, c)
        c.gridx = 0
        c.gridy = 4
        contentPane.add(bt0, c)
        c.gridx = 1
        contentPane.add(bteq, c)
        c.gridx = 2
        contentPane.add(btadd, c)
        c.gridx = 0
        c.gridy = 5
        contentPane.add(btsub, c)
        c.gridx = 1
        contentPane.add(btmul, c)
        c.gridx = 2
        contentPane.add(btdiv, c)
        c.gridx = 0
        c.gridy = 6
        contentPane.add(btopen, c)
        c.gridx = 1
        contentPane.add(btclose, c)
        c.gridx = 2
        contentPane.add(btabs, c)
        c.gridx = 0
        c.gridy = 7
        contentPane.add(btaddlist, c)
        c.gridx = 1
        contentPane.add(btclearlist, c)
        c.gridx = 2
        contentPane.add(btinfo, c)
        c.gridx = 3
        c.gridy = 0
        c.gridheight = 8
        val list = JList(vlist)
        contentPane.add(list, c)
    }
}

private fun createAndShowGUI() {

    val frame = MainWindow("Simple")
    frame.isVisible = true
}

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowGUI)
}