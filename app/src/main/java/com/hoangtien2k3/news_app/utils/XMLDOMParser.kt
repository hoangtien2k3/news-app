package com.hoangtien2k3.news_app.utils

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XMLDOMParser {
    // Returns the entire XML document
    fun getDocument(inputStream: InputStream): Document? {
        val document: Document?
        val factory = DocumentBuilderFactory.newInstance()
        try {
            val db: DocumentBuilder = factory.newDocumentBuilder()
            val inputSource = InputSource(inputStream)
            document = db.parse(inputSource)
        } catch (e: ParserConfigurationException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: SAXException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: IOException) {
            Log.e("Error: ", e.message, e)
            return null
        }
        return document
    }

    fun getDocument(xml: String): Document? {
        var document: Document? = null
        val factory = DocumentBuilderFactory.newInstance()
        try {
            val db: DocumentBuilder = factory.newDocumentBuilder()
            val inputSource = InputSource()
            inputSource.characterStream = StringReader(xml)
            inputSource.encoding = "UTF-8"
            document = db.parse(inputSource)
        } catch (e: ParserConfigurationException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: SAXException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: IOException) {
            Log.e("Error: ", e.message, e)
            return null
        }
        return document
    }


    fun getValue(item: Element, name: String): String {
        val nodes: NodeList = item.getElementsByTagName(name)
        return getTextNodeValue(nodes.item(0))
    }

    private fun getTextNodeValue(node: Node?): String {
        var child: Node?
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.textContent
                    }
                    child = child.nextSibling
                }
            }
        }
        return ""
    }

}
