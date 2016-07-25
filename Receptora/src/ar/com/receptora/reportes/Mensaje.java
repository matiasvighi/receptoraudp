package ar.com.receptora.reportes;

public abstract class Mensaje {
	
	private String header;
	private String idEquipo;
	private String idMensaje;
	private String checksum;
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return the idEquipo
	 */
	public String getIdEquipo() {
		return idEquipo;
	}
	/**
	 * @param idEquipo the idEquipo to set
	 */
	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}
	/**
	 * @return the idMensaje
	 */
	public String getIdMensaje() {
		return idMensaje;
	}
	/**
	 * @param idMensaje the idMensaje to set
	 */
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}