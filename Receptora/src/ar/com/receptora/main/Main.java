package ar.com.receptora.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.InetAddress;

public class Main {
	public static void main(String argv[]) {

		DatagramSocket socket;
		// boolean fin = false;

		try {
			// Creamos el socket
			socket = new DatagramSocket(9230);

			byte[] mensaje_bytes = new byte[256];
			String mensaje = "";
			mensaje = new String(mensaje_bytes);
			// String mensajeComp = "";

			DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);
			DatagramPacket envpaquete = new DatagramPacket(mensaje_bytes, 256);

			int puerto;
			InetAddress address;
			byte[] mensaje2_bytes = new byte[256];
			// Para prueba mierdosa de doble checksum
			byte[] mensaje3_bytes = new byte[256];

			// Iniciamos el bucle
			do {
				// Recibimos el paquete
				socket.receive(paquete);
				// Lo formateamos
				mensaje = new String(mensaje_bytes).trim();
				// Lo mostramos por pantalla
				System.out.println(mensaje);
				// Obtenemos IP Y PUERTO
				puerto = paquete.getPort();
				address = paquete.getAddress();

				CharSequence searchStr = "Caud1"; // 0kCaud1
				CharSequence searchStr2 = "Caud2"; // 0k
				CharSequence searchStr3 = "156p"; // 0k
				CharSequence searchStr4 = "RIN52"; // OK
				CharSequence searchStr5 = "RIN19";
				CharSequence searchStr6 = "RIN21"; // 0k
				CharSequence searchStr7 = "RIN43";
				if (mensaje.contains(searchStr)) {
					System.out.println("Aca está el equipo 1 Guacho");

					// escribe el log

					// File file = new
					// File("/home/matias/Logpaninotemp/GT0998log.txt");

					// prueba
					FileWriter fichero = null;
					PrintWriter pw = null;
					try {
						fichero = new FileWriter("/var/www/Receptora/inputfile/Caud1.txt", true);
						pw = new PrintWriter(fichero);
						// int i = 0;
						// for ( ; i < 10; i++)
						pw.println(mensaje);

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// Nuevamente aprovechamos el finally para
						// asegurarnos que se cierra el fichero.
						if (null != fichero)
							fichero.close();
						try {
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}

					// Prueba de checsum 2

					byte checksum = 0;
					int len = mensaje.length();

					for (int i = 0; i < len; i++) {
						checksum ^= mensaje.charAt(i);
						String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF)).replace(' ',
								'0');
						// System.out.println("Index: " + i
						// + " - Checksum: || ASCII Value:"
						// + ((checksum != 0) ? (char) checksum : '0')
						// + " || Int Value: " + (int) checksum
						// + " || Binary Value: " + binaryValue);
						if (i != 0) {
							if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
								break;
							}
						}
					}

					// esto es para separar los datos del caudalímetro del resto
					// del paquete

					// variable de la pos de gmaps
					String PosArmada = "";

					CharSequence revTiPaq = "RCQ99";

					if (mensaje.contains(revTiPaq)) {

						String[] datosMensaje1 = mensaje.split(";");

						// Este es el dato de la fecha y hora

						String mensajeFechHor = mensaje;
						String FechaDia = mensajeFechHor.substring(6, 8);
						String FechaMes = mensajeFechHor.substring(8, 10);
						String FechaAno = mensajeFechHor.substring(10, 12);
						String HoraGmt = mensajeFechHor.substring(12, 14);
						String Min = mensajeFechHor.substring(14, 16);
						String Seg = mensajeFechHor.substring(16, 18);
						String Nev = mensajeFechHor.substring(3, 6);
						int HoraGmt1 = Integer.parseInt(HoraGmt);
						int menos3 = 3;
						int hora = HoraGmt1 - menos3;

						String Fechhor = FechaDia + "/" + FechaMes + "/" + FechaAno + "|" + hora + ":" + Min + ":"
								+ Seg;

						System.out.println("Fecha y hora");
						System.out.println(Fechhor);

						// este es el dato del caudalímetro

						String mensajeCaud = datosMensaje1[1];
						System.out.println("mensajeCaud:");
						System.out.println(mensajeCaud);
						// Subdividimos el dato del caudalímetro

						String[] mensajeCaudSubd = mensajeCaud.split(",");

						String DatoCaud1 = mensajeCaudSubd[0];
						String DatoCaud2 = mensajeCaudSubd[1];
						String DatoCaud3 = mensajeCaudSubd[2];
						String DatoCaud4 = mensajeCaudSubd[3];
						String DatoCaud5 = mensajeCaudSubd[4];

						// Corto el label "TXT"
						String[] mensajeCaudSubd1 = mensajeCaudSubd[0].split("=");

						String NoTxtLabel = mensajeCaudSubd1[1];

						System.out.println(NoTxtLabel);
						System.out.println(DatoCaud2);
						System.out.println(DatoCaud3);
						System.out.println(DatoCaud4);
						System.out.println(DatoCaud5);

						// Aca se selecionas si se usa css o el html crudo.

						// String DatoCaudComp = " Remito Camesa: "+ NoTxtLabel+
						// " |Numero de ticket: "+ DatoCaud2+ " |Volumen
						// acumulado (litros): "+ DatoCaud3+ " |Temperatura: "+
						// DatoCaud4;
						String DatoCaudComp = "|" + NoTxtLabel + "|" + DatoCaud2 + "|" + DatoCaud3 + "|" + DatoCaud4;

						// Porqueria para poner link Google

						String[] gmapPos = mensaje.split("-");
						String LatGog = gmapPos[1];
						String LongGog = gmapPos[2];

						String LatGog1 = LatGog.substring(0, 2);
						String LongGog1 = LongGog.substring(1, 3);
						String LatGog2 = LatGog.substring(2, 7);
						String LongGog2 = LongGog.substring(3, 8);
						PosArmada = "http://maps.google.com/maps?f=q&q=-" + LatGog1 + "." + LatGog2 + "-" + LongGog1
								+ "." + LongGog2 + "&om=1&z=17";

						String DatoCompleto = PosArmada + Nev + "|" + Fechhor;

						System.out.println(DatoCaudComp);
						System.out.println(Nev);

						FileWriter fichero2 = null;
						PrintWriter pw2 = null;
						try {
							fichero2 = new FileWriter("/mnt/Gps/Caudalimetros/Datosdelmismo/prueba1.txt",
									// "/var/www/Caudalimetros/inputfile/prueba1.txt",
									true);
							pw2 = new PrintWriter(fichero2);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw2.println(DatoCompleto);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero2)
								fichero2.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();

							}

						}
					}

					// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

					// Esto es para parcear ciertos datos ATENTI!!!

					String[] datosMensaje = mensaje.split(";");

					// esto es para enviar el paquete de respuesta ACK

					String Checks1 = datosMensaje[1];
					String Checks2 = datosMensaje[2];

					if (mensaje.contains(revTiPaq)) {

						// checsum en caso de reporte extendido por el puto
						// caudalimetro

						Checks1 = datosMensaje[2];
						Checks2 = datosMensaje[3];
						System.out.println(Checks1);

					}

					System.out.println(PosArmada);
					String mensajeComp = ">" + "ACK;" + Checks1 + ";" + Checks2 + ";" + "*";

					System.out.println(mensajeComp);

					byte checksum1 = 0;
					int len1 = mensajeComp.length();

					for (int i = 0; i < len1; i++) {
						checksum1 ^= mensajeComp.charAt(i);
						String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF)).replace(' ',
								'0');
						// System.out.println("Index: " + i
						// + " - Checksum: || ASCII Value:"
						// + ((checksum != 0) ? (char) checksum1 : '0')
						// + " || Int Value: " + (int) checksum1
						// + " || Binary Value: " + binaryValue);
						if (i != 0) {
							if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
								break;
							}
						}
					}

					String Hex1 = Integer.toHexString(checksum1);
					String Hex = Hex1.toUpperCase();
					String Banana = "checksumdesalida";

					System.out.println(checksum1);
					System.out.println(Banana);
					System.out.println(Hex);
					//// String checksum2 = mensajeComp+ Hex+ "<" ;
					String checksum2 = mensajeComp + "<";
					System.out.println(checksum2);

					// ACA VA ENVIAR!!!

					// Obtenemos IP Y PUERTO
					// puerto = paquete.getPort();
					// address = paquete.getAddress();

					// Creamos una instancia BuffererReader en la
					// que guardamos los datos introducido por el usuario

					// BufferedReader in = new BufferedReader(new
					// InputStreamReader(System.in));

					// declaramos e instanciamos un objeto de tipo byte
					// byte[] mensaje_bytes3 = new byte[256];

					// para checksum2
					// byte[] mensaje_bytes4 = new byte[256];
					// declaramos una variable de tipo string

					// mensajeComp=in.readLine();

					// formateamos el mensaje de salida
					mensaje2_bytes = checksum2.getBytes();

					// Preparamos el paquete que queremos enviar
					envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

					// realizamos el envio
					socket.send(envpaquete);

					// } while (1>0);
					// }
					// Checsum doble de prueba chota

					// HASTA ACA!!

					// stop prueba

					// if file doesnt exists, then create it
					// if (!file.exists()) {
					// file.createNewFile();
					// }

					// FileWriter fw = new FileWriter(file.getAbsoluteFile());
					// BufferedWriter bw = new BufferedWriter(fw);
					// bw.write(mensaje);
					// bw.close();

					System.out.println("Done");

				} else {

					System.out.println("Este no es amigo");

					if (mensaje.contains(searchStr2)) {
						System.out.println("Aca está el equipo 2 Guacho");

						// escribe el log
						// prueba
						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/mnt/Gps/LogPanino/NGF422II.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// stop prueba

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum : '0')
							// + " || Int Value: " + (int) checksum
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						// ----------_________________________________________----------------------------

						// esto es para separar los datos del caudalímetro del
						// resto del paquete

						// variable de la pos de gmaps
						String PosArmada = "";

						CharSequence revTiPaq = "RCQ99";

						if (mensaje.contains(revTiPaq)) {

							String[] datosMensaje1 = mensaje.split(";");

							// Este es el dato de la fecha y hora

							String mensajeFechHor = mensaje;
							String FechaDia = mensajeFechHor.substring(6, 8);
							String FechaMes = mensajeFechHor.substring(8, 10);
							String FechaAno = mensajeFechHor.substring(10, 12);
							String HoraGmt = mensajeFechHor.substring(12, 14);
							String Min = mensajeFechHor.substring(14, 16);
							String Seg = mensajeFechHor.substring(16, 18);
							String Nev = mensajeFechHor.substring(3, 6);
							int HoraGmt1 = Integer.parseInt(HoraGmt);
							int menos3 = 3;
							int hora = HoraGmt1 - menos3;

							String Fechhor = FechaDia + "/" + FechaMes + "/" + FechaAno + "|" + hora + ":" + Min + ":"
									+ Seg;

							System.out.println("Fecha y hora");
							System.out.println(Fechhor);

							// este es el dato del caudalímetro

							String mensajeCaud = datosMensaje1[1];
							System.out.println("mensajeCaud:");
							System.out.println(mensajeCaud);
							// Subdividimos el dato del caudalímetro

							String[] mensajeCaudSubd = mensajeCaud.split(",");

							String DatoCaud1 = mensajeCaudSubd[0];
							String DatoCaud2 = mensajeCaudSubd[1];
							String DatoCaud3 = mensajeCaudSubd[2];
							String DatoCaud4 = mensajeCaudSubd[3];
							String DatoCaud5 = mensajeCaudSubd[4];
							String DatoCaud6 = mensajeCaudSubd[5];
							String DatoCaud7 = mensajeCaudSubd[6];
							String DatoCaud8 = mensajeCaudSubd[7];
							String DatoCaud9 = mensajeCaudSubd[8];
							String DatoCaud10 = mensajeCaudSubd[9];
							String DatoCaud11 = mensajeCaudSubd[10];
							// String DatoCaud12 = mensajeCaudSubd[11];

							// Corto el label "TXT"
							String[] mensajeCaudSubd1 = mensajeCaudSubd[0].split("=");

							String NoTxtLabel = mensajeCaudSubd1[1];

							System.out.println(NoTxtLabel);
							System.out.println(DatoCaud2);
							System.out.println(DatoCaud3);
							System.out.println(DatoCaud4);
							System.out.println(DatoCaud5);
							System.out.println(DatoCaud6);
							System.out.println(DatoCaud7);
							System.out.println(DatoCaud8);
							System.out.println(DatoCaud9);
							System.out.println(DatoCaud10);
							// System.out.println(DatoCaud11);

							// Aca se selecionas si se usa css o el html crudo.

							// String DatoCaudComp = " Remito Camesa: "+
							// NoTxtLabel+ " |Numero de ticket: "+ DatoCaud2+ "
							// |Volumen acumulado (litros): "+ DatoCaud3+ "
							// |Temperatura: "+ DatoCaud4;
							String DatoCaudComp = "|" + NoTxtLabel + "|" + DatoCaud2 + "|" + DatoCaud3 + "|" + DatoCaud4
									+ DatoCaud5 + "|" + DatoCaud6 + "|" + DatoCaud7 + "|" + DatoCaud8 + "|" + DatoCaud9
									+ "|" + DatoCaud10 + "|";

							// Porqueria para poner link Google

							String[] gmapPos = mensaje.split("-");
							String LatGog = gmapPos[1];
							String LongGog = gmapPos[2];

							String LatGog1 = LatGog.substring(0, 2);
							String LongGog1 = LongGog.substring(1, 3);
							String LatGog2 = LatGog.substring(2, 7);
							String LongGog2 = LongGog.substring(3, 8);
							PosArmada = "http://maps.google.com/maps?f=q&q=-" + LatGog1 + "." + LatGog2 + "-" + LongGog1
									+ "." + LongGog2 + "&om=1&z=17";

							String DatoCompleto = PosArmada + DatoCaudComp + "|" + Fechhor;

							System.out.println(DatoCaudComp);
							System.out.println(Nev);

							FileWriter fichero2 = null;
							PrintWriter pw2 = null;
							try {
								fichero2 = new FileWriter("/mnt/Gps/Caudalimetros/Datosdelmismo/prueba1.txt",
										// "/var/www/Caudalimetros/inputfile/prueba1.txt",
										true);
								pw2 = new PrintWriter(fichero2);
								// int i = 0;
								// for ( ; i < 10; i++)
								pw2.println(DatoCompleto);

							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								// Nuevamente aprovechamos el finally para
								// asegurarnos que se cierra el fichero.
								if (null != fichero2)
									fichero2.close();
								try {
								} catch (Exception e2) {
									e2.printStackTrace();

								}

							}
						}

						// ____________________--------------------------_______________________________-----------

						// CharSequence revTiPaq = "RCQ99";

						// if (mensaje.contains(revTiPaq)) {

						// String temp1 = mensaje.substring(70,75);
						// String temp2 = mensaje.substring(77,82);
						// En ese caso, aux2 va a tener "stri"
						// String aux2 = aux.substring(4, 7);
						// En este otro caso va a tener "ngDe"

						// System.out.println(temp1);
						// System.out.println(temp2);

						// String temp = temp1+ ";"+ temp2;

						// System.out.println(temp);

						// FileWriter fichero2 = null;
						// PrintWriter pw2 = null;
						// try {
						// fichero2 = new FileWriter(
						// "/mnt/Gps/LogPanino/Temperaturas/NGF422.csv",
						// true);
						// pw2 = new PrintWriter(fichero2);
						// int i = 0;
						// for ( ; i < 10; i++)
						// pw2.println(temp);

						// } catch (Exception e) {
						// e.printStackTrace();
						// } finally {
						// Nuevamente aprovechamos el finally para
						// asegurarnos que se cierra el fichero.
						// if (null != fichero2)
						// fichero2.close();
						// try {
						// } catch (Exception e2) {
						// e2.printStackTrace();

						// }}
						// }

						// aca se termina lo que separa la temp del resto del
						// paquete
						// comentar la linea siguiente para que ande como
						// corresponde

						// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String Checks1 = datosMensaje[1];
						String Checks2 = datosMensaje[2];

						if (mensaje.contains(revTiPaq)) {

							// checsum en caso de reporte extendido por el puto
							// caudalimetro

							Checks1 = datosMensaje[2];
							Checks2 = datosMensaje[3];
							System.out.println(Checks1);

						}

						// System.out.println(PosArmada);
						String mensajeComp = ">" + "ACK;" + Checks1 + ";" + Checks2 + ";" + "*";

						System.out.println(mensajeComp);

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum1 : '0')
							// + " || Int Value: " + (int) checksum1
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!!
						// File file = new
						// File("/home/matias/Logpaninotemp/GTO852log.txt");

						// if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }

						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						System.out.println("Done");

					} else {

						System.out.println("Este no es amigo");
					}

					if (mensaje.contains(searchStr3)) {
						System.out.println("Aca está el equipo 3 Guacho");
						// escribe el log

						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/mnt/Gps/LogPanino/MSV209IIlog.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum : '0')
							// + " || Int Value: " + (int) checksum
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}

						// ----------_________________________________________----------------------------

						// esto es para separar los datos del caudalímetro del
						// resto del paquete

						// variable de la pos de gmaps
						String PosArmada = "";

						CharSequence revTiPaq = "RCQ99";

						if (mensaje.contains(revTiPaq)) {

							String[] datosMensaje1 = mensaje.split(";");

							// Este es el dato de la fecha y hora

							String mensajeFechHor = mensaje;
							String FechaDia = mensajeFechHor.substring(6, 8);
							String FechaMes = mensajeFechHor.substring(8, 10);
							String FechaAno = mensajeFechHor.substring(10, 12);
							String HoraGmt = mensajeFechHor.substring(12, 14);
							String Min = mensajeFechHor.substring(14, 16);
							String Seg = mensajeFechHor.substring(16, 18);
							String Nev = mensajeFechHor.substring(3, 6);
							int HoraGmt1 = Integer.parseInt(HoraGmt);
							int menos3 = 3;
							int hora = HoraGmt1 - menos3;

							String Fechhor = FechaDia + "/" + FechaMes + "/" + FechaAno + "|" + hora + ":" + Min + ":"
									+ Seg;

							System.out.println("Fecha y hora");
							System.out.println(Fechhor);

							// este es el dato del caudalímetro

							String mensajeCaud = datosMensaje1[1];
							System.out.println("mensajeCaud:");
							System.out.println(mensajeCaud);
							// Subdividimos el dato del caudalímetro

							String[] mensajeCaudSubd = mensajeCaud.split(",");

							String DatoCaud1 = mensajeCaudSubd[0];
							String DatoCaud2 = mensajeCaudSubd[1];
							String DatoCaud3 = mensajeCaudSubd[2];
							String DatoCaud4 = mensajeCaudSubd[3];
							String DatoCaud5 = mensajeCaudSubd[4];
							String DatoCaud6 = mensajeCaudSubd[5];
							String DatoCaud7 = mensajeCaudSubd[6];
							String DatoCaud8 = mensajeCaudSubd[7];
							String DatoCaud9 = mensajeCaudSubd[8];
							String DatoCaud10 = mensajeCaudSubd[9];
							String DatoCaud11 = mensajeCaudSubd[10];
							// String DatoCaud12 = mensajeCaudSubd[11];

							// Corto el label "TXT"
							String[] mensajeCaudSubd1 = mensajeCaudSubd[0].split("=");

							String NoTxtLabel = mensajeCaudSubd1[1];

							System.out.println(NoTxtLabel);
							System.out.println(DatoCaud2);
							System.out.println(DatoCaud3);
							System.out.println(DatoCaud4);
							System.out.println(DatoCaud5);
							System.out.println(DatoCaud6);
							System.out.println(DatoCaud7);
							System.out.println(DatoCaud8);
							System.out.println(DatoCaud9);
							System.out.println(DatoCaud10);
							// System.out.println(DatoCaud11);

							// Aca se selecionas si se usa css o el html crudo.

							// String DatoCaudComp = " Remito Camesa: "+
							// NoTxtLabel+ " |Numero de ticket: "+ DatoCaud2+ "
							// |Volumen acumulado (litros): "+ DatoCaud3+ "
							// |Temperatura: "+ DatoCaud4;
							String DatoCaudComp = "|" + NoTxtLabel + "|" + DatoCaud2 + "|" + DatoCaud3 + "|" + DatoCaud4
									+ DatoCaud5 + "|" + DatoCaud6 + "|" + DatoCaud7 + "|" + DatoCaud8 + "|" + DatoCaud9
									+ "|" + DatoCaud10 + "|";

							// Porqueria para poner link Google

							String[] gmapPos = mensaje.split("-");
							String LatGog = gmapPos[1];
							String LongGog = gmapPos[2];

							String LatGog1 = LatGog.substring(0, 2);
							String LongGog1 = LongGog.substring(1, 3);
							String LatGog2 = LatGog.substring(2, 7);
							String LongGog2 = LongGog.substring(3, 8);
							PosArmada = "http://maps.google.com/maps?f=q&q=-" + LatGog1 + "." + LatGog2 + "-" + LongGog1
									+ "." + LongGog2 + "&om=1&z=17";

							String DatoCompleto = PosArmada + DatoCaudComp + "|" + Fechhor;

							System.out.println(DatoCaudComp);
							System.out.println(Nev);

							FileWriter fichero2 = null;
							PrintWriter pw2 = null;
							try {
								fichero2 = new FileWriter("/mnt/Gps/Caudalimetros/Datosdelmismo/prueba1.txt",
										// "/var/www/Caudalimetros/inputfile/prueba1.txt",
										true);
								pw2 = new PrintWriter(fichero2);
								// int i = 0;
								// for ( ; i < 10; i++)
								pw2.println(DatoCompleto);

							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								// Nuevamente aprovechamos el finally para
								// asegurarnos que se cierra el fichero.
								if (null != fichero2)
									fichero2.close();
								try {
								} catch (Exception e2) {
									e2.printStackTrace();

								}

							}
						}

						// ____________________--------------------------_______________________________-----------

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String Checks1 = datosMensaje[1];
						String Checks2 = datosMensaje[2];

						if (mensaje.contains(revTiPaq)) {

							// checsum en caso de reporte extendido por el puto
							// caudalimetro

							Checks1 = datosMensaje[2];
							Checks2 = datosMensaje[3];
							System.out.println(Checks1);

						}

						else {
							String mensajeComp = ">" + "ACK;" + datosMensaje[1] + ";" + datosMensaje[2] + ";" + "*";
							// String mensajeComp= ">"+ "ACK;"+datosMensaje[5]+
							// ";"+ datosMensaje[6]+ ";"+ "*";

							System.out.println(mensajeComp);
						}

						// System.out.println(PosArmada);

						String mensajeComp = ">" + "ACK;" + Checks1 + ";" + Checks2 + ";" + "*";

						System.out.println(mensajeComp);

						// calcula el checsum saliente (ack)

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum1 : '0')
							// + " || Int Value: " + (int) checksum1
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!! // File file = new
						// File("/home/matias/Logpaninotemp/GT119log.txt");

						// if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }

						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						System.out.println("Done");

					}

					else {

						System.out.println("Este no es amigo");
					}
					if (mensaje.contains(searchStr4)) {
						System.out.println("Aca está el equipo 4 Guacho");
						// escribe el log

						// prueba
						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/home/matias/Logpaninotemp/ORN704log.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum : '0')
							// + " || Int Value: " + (int) checksum
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						// comentar la linea siguiente para que ande como
						// corresponde

						// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String mensajeComp = ">" + "ACK;" + datosMensaje[1] + ";" + datosMensaje[2] + ";" + "*";
						// String mensajeComp= ">"+ "ACK;"+datosMensaje[5]+ ";"+
						// datosMensaje[6]+ ";"+ "*";

						System.out.println(mensajeComp);

						// calcula el checsum saliente (ack)

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum1 : '0')
							// + " || Int Value: " + (int) checksum1
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!!// stop prueba

						// File file = new
						// File("/home/matias/Logpaninotemp/GT118log.txt");

						// if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }

						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						System.out.println("Done");

					} else {

						System.out.println("Este no es amigo");
					}

					if (mensaje.contains(searchStr5)) {
						System.out.println("Aca está el equipo 5 Guacho");
						// escribe el log

						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/home/matias/Logpaninotemp/HPE505log.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum : '0')
							// + " || Int Value: " + (int) checksum
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						// comentar la linea siguiente para que ande como
						// corresponde

						// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String mensajeComp = ">" + "ACK;" + datosMensaje[1] + ";" + datosMensaje[2] + ";" + "*";
						// String mensajeComp= ">"+ "ACK;"+datosMensaje[5]+ ";"+
						// datosMensaje[6]+ ";"+ "*";

						System.out.println(mensajeComp);

						// calcula el checsum saliente (ack)

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum1 : '0')
							// + " || Int Value: " + (int) checksum1
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!!// File file = new
						// File("/home/matias/Logpaninotemp/GT1136log.txt");

						// if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }
						//
						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						System.out.println("Done");

					} else {

						System.out.println("Este no es amigo");
					}

					if (mensaje.contains(searchStr6)) {
						System.out.println("Aca está el equipo 6 Guacho");
						// escribe el log

						// prueba
						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/home/matias/Logpaninotemp/GTC627log.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// stop prueba

						// File file = new
						// File("/home/matias/Logpaninotemp/GT118log.txt");

						// if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }

						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						// System.out.println("Done");
						// char checksum = 0;
						// int len = mensaje.length();
						// for (int i = 0; i < len; i++) {
						// checksum ^= mensaje.charAt(i);
						// if(i!=0){
						// if ((mensaje.charAt(i-1) == ';') & (mensaje.charAt(i)
						// == '*')) break;
						// }
						// }
						// System.out.println(checksum);

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							System.out.println("Index: " + i + " - Checksum: || ASCII Value:"
									+ ((checksum != 0) ? (char) checksum : '0') + " || Int Value: " + (int) checksum
									+ " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						// comentar la linea siguiente para que ande como
						// corresponde

						// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String mensajeComp = ">" + "ACK;" + datosMensaje[1] + ";" + datosMensaje[2] + ";" + "*";
						// String mensajeComp= ">"+ "ACK;"+datosMensaje[5]+ ";"+
						// datosMensaje[6]+ ";"+ "*";

						System.out.println(mensajeComp);

						// calcula el checsum saliente (ack)

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							System.out.println("Index: " + i + " - Checksum: || ASCII Value:"
									+ ((checksum != 0) ? (char) checksum1 : '0') + " || Int Value: " + (int) checksum1
									+ " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!!
					} else {

						System.out.println("Este no es amigo");
					}
					if (mensaje.contains(searchStr7)) {
						System.out.println("Aca está el equipo 7 (rinho) Guacho");
						// escribe el log

						// File file = new
						// File("/home/matias/Logpaninotemp/GT0998log.txt");

						// pruebamensajeComp=in.readLine();

						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter("/home/matias/Logpaninotemp/NGF423log.txt", true);
							pw = new PrintWriter(fichero);
							// int i = 0;
							// for ( ; i < 10; i++)
							pw.println(mensaje);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// Nuevamente aprovechamos el finally para
							// asegurarnos que se cierra el fichero.
							if (null != fichero)
								fichero.close();
							try {
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						// stop prueba

						// Prueba de checsum 2

						byte checksum = 0;
						int len = mensaje.length();

						for (int i = 0; i < len; i++) {
							checksum ^= mensaje.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum : '0')
							// + " || Int Value: " + (int) checksum
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						// comentar la linea siguiente para que ande como
						// corresponde

						// mensaje=">RCY00020205000000-2778467-06425658000000-000090;DFFFFFF;IGN1;IN00;XP00;#0021;ID=1234;*59<";

						// Esto es para parcear ciertos datos ATENTI!!!
						System.out.println(checksum);

						String[] datosMensaje = mensaje.split(";");

						// esto es para enviar el paquete de respuesta ACK

						String mensajeComp = ">" + "ACK;" + datosMensaje[1] + ";" + datosMensaje[2] + ";" + "*";
						// String mensajeComp= ">"+ "ACK;"+datosMensaje[5]+ ";"+
						// datosMensaje[6]+ ";"+ "*";

						System.out.println(mensajeComp);

						// calcula el checsum saliente (ack)

						byte checksum1 = 0;
						int len1 = mensajeComp.length();

						for (int i = 0; i < len1; i++) {
							checksum1 ^= mensajeComp.charAt(i);
							String binaryValue = String.format("%8s", Integer.toBinaryString(checksum1 & 0xFF))
									.replace(' ', '0');
							// System.out.println("Index: " + i
							// + " - Checksum: || ASCII Value:"
							// + ((checksum != 0) ? (char) checksum1 : '0')
							// + " || Int Value: " + (int) checksum1
							// + " || Binary Value: " + binaryValue);
							if (i != 0) {
								if ((mensaje.charAt(i - 1) == ';') & (mensaje.charAt(i) == '*')) {
									break;
								}
							}
						}
						String Hex1 = Integer.toHexString(checksum1);
						String Hex = Hex1.toUpperCase();
						String Banana = "checksumdesalida";

						System.out.println(checksum1);
						System.out.println(Banana);
						System.out.println(Hex);
						String checksum2 = mensajeComp + Hex + "<";

						System.out.println(checksum2);

						// ACA VA ENVIAR!!!

						// Obtenemos IP Y PUERTO
						// puerto = paquete.getPort();
						// address = paquete.getAddress();

						// Creamos una instancia BuffererReader en la
						// que guardamos los datos introducido por el usuario

						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(System.in));

						// declaramos e instanciamos un objeto de tipo byte
						byte[] mensaje_bytes3 = new byte[256];

						// declaramos una variable de tipo string

						// mensajeComp=in.readLine();

						// formateamos el mensaje de salida
						mensaje2_bytes = checksum2.getBytes();

						// Preparamos el paquete que queremos enviar
						envpaquete = new DatagramPacket(mensaje2_bytes, checksum2.length(), address, puerto);

						// realizamos el envio
						socket.send(envpaquete);

						// } while (1>0);
						// }

						// HASTA ACA!! // if file doesnt exists, then create it
						// if (!file.exists()) {
						// file.createNewFile();
						// }

						// FileWriter fw = new
						// FileWriter(file.getAbsoluteFile());
						// BufferedWriter bw = new BufferedWriter(fw);
						// bw.write(mensaje);
						// bw.close();

						System.out.println("Done");

					} else {

						System.out.println("Este no es amigo");
					}
				}

				// ---------------------------------------------------------------------------------

				// Reenvio a Geosystem

				//
				CharSequence SearchAck = "ACK"; // esto vino de geo no se por
												// que

				if (!mensaje.contains(SearchAck)) { // revisar el negador ! anda
													// mal.

					// mirar recep pasamanos

					// declaramos e instanciamos un objeto de tipo byte
					byte[] mensaje_bytes5 = new byte[256];
					// formateamos el mensaje de salida
					mensaje3_bytes = mensaje.getBytes();

					// Declaro IP De Geo

					int portcompgps = 9230;

					String diregeo = "190.210.181.22";
					DatagramSocket socketCliente = null;
					try {
						socketCliente = new DatagramSocket();
					} catch (IOException e) {
						System.out.println("Error al crear el objeto socket cliente");
						System.exit(0);
					}
					InetAddress DireccionIP = null;
					try {
						DireccionIP = InetAddress.getByName(diregeo);
					} catch (IOException e) {
						System.out.println("Error al recuperar la IP del proceso");
						System.exit(0);
					}
					System.out.println("Reenvié a Geo el paquete");

					// Preparamos el paquete que queremos enviar
					// envpaquete = new
					// DatagramPacket(mensaje3_bytes,mensaje.length(),DireccionIP,puerto);

					DatagramPacket enviarPaquete = new DatagramPacket(mensaje_bytes, mensaje.length(), DireccionIP,
							portcompgps);
					// socketCliente.send(enviarPaquete);
					// realizamos el envio
					socket.send(enviarPaquete);

				} else {

					System.out.println("Paquete Ack");
					// -----------------------------------------------------------------------------------

				}
			} while (1 > 0);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
