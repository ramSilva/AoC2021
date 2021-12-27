package puzzles

import java.io.File

private val lines = File("input/puzzle16/input.txt").readLines().toMutableList()

fun String.toBinary() = this.fold("") { acc, c ->
    var digit = c.digitToInt(16).toString(2)
    val pad = if (digit.length % 4 == 0) 0 else 4 - (digit.length % 4)
    digit = "000".substring(0, pad) + digit

    acc + digit
}

fun Packet.sumVersions(): Int =
    if (this is Operator) this.version + this.subpackets.sumOf { it.sumVersions() }
    else this.version

fun Packet.print(padding: Int) {
    var prt = """typeID: ${this.type}"""
    prt = prt.padStart(prt.length + padding, ' ')
    println(prt)
    if (this is Operator) {
        this.subpackets.forEach { it.print(padding + 1) }
    } else {
        prt = """-> ${(this as Literal).value.toLong(2)}"""
        prt = prt.padStart(prt.length + padding, ' ')
        println(prt)
    }
}

fun Packet.calculate(): Long =
    if (this is Operator) {
        when (this.type) {
            0 -> this.subpackets.sumOf { it.calculate() }
            1 -> this.subpackets.fold(1L) { acc, packet -> acc * packet.calculate() }
            2 -> this.subpackets.minOf { it.calculate() }
            3 -> this.subpackets.maxOf { it.calculate() }
            5 -> if (this.subpackets[0].calculate() > this.subpackets[1].calculate()) 1 else 0
            6 -> if (this.subpackets[0].calculate() < this.subpackets[1].calculate()) 1 else 0
            7 -> if (this.subpackets[0].calculate() == this.subpackets[1].calculate()) 1 else 0
            else -> 0
        }
    } else {
        (this as Literal).value.toLong(2)
    }

open class Packet(
    var version: Int = 0,
    var type: Int = 0,
    var length: Int = 0
)

class Literal(
    var value: String = ""
) : Packet()

class Operator(
    var lengthType: Int = 0,
    var subpackets: MutableList<Packet> = mutableListOf()
) : Packet()


fun processPacket(string: String, padLiteral: Boolean): Packet {
    var bin = string

    val version = bin.take(3).toInt(2)
    bin = bin.substring(3)

    val type = bin.take(3).toInt(2)
    bin = bin.substring(3)

    var packetLength = 6

    if (type == 4) {
        val packet = Literal()
        packet.version = version
        packet.type = type

        while (true) {
            packet.value += bin.substring(1, 5)
            packetLength += 5

            if (bin[0] == '0') {
                if (padLiteral) packetLength += 4 - (packetLength % 4)
                packet.length = packetLength
                return packet
            }
            bin = bin.substring(5)
        }
    } else {
        val packet = Operator()

        packet.lengthType = bin.take(1).toInt(2)
        bin = bin.substring(1)
        packetLength += 1

        if (packet.lengthType == 0) {
            var subpacketLength = 0

            val expectedLength = bin.take(15).toInt(2)
            bin = bin.substring(15)
            packetLength += 15

            while (subpacketLength < expectedLength) {
                val subpacket = processPacket(bin, false)
                packet.subpackets.add(subpacket)
                bin = bin.substring(subpacket.length)
                packetLength += subpacket.length

                subpacketLength += subpacket.length
            }
        } else {
            val expectedSubpackets = bin.take(11).toInt(2)
            bin = bin.substring(11)
            packetLength += 11

            var subpackets = 0

            while (subpackets < expectedSubpackets) {
                val subpacket = processPacket(bin, false)
                packet.subpackets.add(subpacket)
                bin = bin.substring(subpacket.length)
                packetLength += subpacket.length

                subpackets++
            }
        }

        packet.version = version
        packet.type = type
        packet.length = packetLength
        return packet
    }
}

fun puzzle16() = processPacket(lines[0].toBinary(), true).sumVersions()

fun puzzle16dot1() = processPacket(lines[0].toBinary(), true).also { it.print(0) }.calculate()